/*
 * Copyright 2015 AppDynamics, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdynamics.inventory;

import com.appdynamicspilot.exception.InventoryServerException;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.PrintWriter;
import java.io.StringWriter;


public class OrderDaoImpl implements OrderDao {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Long createOrder(OrderRequest orderRequest) throws InventoryServerException {
        //InventoryItem item = getEntityManager().find(InventoryItem.class,orderRequest.getItemId());
        try {
        if (orderRequest != null)
            return processOrder(orderRequest);
        else
            logger.info("OrderRequest is null");
        } catch (Exception e) {
            logger.error(e.getMessage());
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            e.printStackTrace(pw);
            String errorDetail = writer.toString();
            logger.error(errorDetail);
        }
        return new Long(0);
    }

    private Long processOrder(OrderRequest orderRequest) {
        try {
            EntityManager entityManager = getEntityManagerFactory().createEntityManager();
            if (entityManager != null) {
                InventoryItem item = entityManager.find(InventoryItem.class,
                        orderRequest.getItemId());

                if (item != null) {
                    Order order = new Order(orderRequest, item);
                    if (order != null) {
                        order.setQuantity(orderRequest.getQuantity());

                        logger.info("order stored is: " + order.getId() + " " + order.getQuantity() + " " + order.getCreatedOn());

                        entityManager.getTransaction().begin();
                        entityManager.persist(order);
                        entityManager.getTransaction().commit();

                        Thread.sleep(500);

                        entityManager.getTransaction().begin();
                        entityManager.remove(order);
                        entityManager.getTransaction().commit();

                        logger.info("order created is: " + order.getId() + " " + order.getQuantity() + " " + order.getCreatedOn());
                        return order.getId();
                    }
                }
                entityManager.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            e.printStackTrace(pw);
            String errorDetail = writer.toString();
            logger.error(errorDetail);
        }
        return new Long(0);
    }

}