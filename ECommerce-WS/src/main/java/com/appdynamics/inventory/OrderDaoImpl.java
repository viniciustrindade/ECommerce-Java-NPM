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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;


public class OrderDaoImpl implements OrderDao {
    public static final int SLOW_BOOK = 3;
    private Logger logger = Logger.getLogger(OrderDaoImpl.class);
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private String selectQuery = null;

    private Client client = null;
    private WebTarget webTarget = null;

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public synchronized EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Long createOrder(OrderRequest orderRequest) throws InventoryServerException {
        InventoryItem item = getEntityManager().find(InventoryItem.class, orderRequest.getItemId());
        Response ccResponse = null;
        String responseFromCCPayment = null;
        if (orderRequest.getItemId() == 5) {
            throw new InventoryServerException("Error in creating order for " + item.getId(), null);
        }
        client = ClientBuilder.newClient();

        try {
            logger.info(urlHelper());


            webTarget = client.target(UriBuilder.fromUri(urlHelper()).build());
            ccResponse = webTarget.request().accept(MediaType.TEXT_PLAIN).get(Response.class);

            responseFromCCPayment = ccResponse.readEntity(String.class);
            logger.info(responseFromCCPayment);

            if (responseFromCCPayment.equalsIgnoreCase("success")) {
                logger.info("The order request has been created due to successful credit card info.");
                return storeOrder(orderRequest);
            } else {
                logger.info("Order failed due to invalid card information");
            }

        } catch (IOException e) {
            logger.error(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Long storeOrder(OrderRequest orderRequest) {
        InventoryItem item = entityManager.find(InventoryItem.class, orderRequest.getItemId());
        Order order = new Order(orderRequest, item);

        order.setQuantity(orderRequest.getQuantity());
        persistOrder(order);
        //deleting the order to reduce size of data
        removeOrder(order);
        return order.getId();
    }

    private void persistOrder(Order order) {
        EntityTransaction txn = getEntityManager().getTransaction();
        try {
            txn.begin();
            entityManager.persist(order);
        } catch (Exception ex) {
            logger.error(ex);
            txn.rollback();
        } finally {
            if (!txn.getRollbackOnly()) {
                txn.commit();
            }
        }
    }

    private void removeOrder(Order order) {
        EntityTransaction txn = getEntityManager().getTransaction();
        try {
            txn.begin();
            entityManager.remove(order);
        } catch (Exception ex) {
            logger.error(ex);
            txn.rollback();
        } finally {
            if (!txn.getRollbackOnly()) {
                txn.commit();
            }
        }
    }

    /**
     * @param selectQuery the selectQuery to set
     */
    public void setSelectQuery(String selectQuery) {
        this.selectQuery = selectQuery;
    }

    private String urlHelper() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";
        String ccPaymentUrl = null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

        // get the property value and print it out
        ccPaymentUrl = prop.getProperty("ccPaymentUrl");
        logger.info(ccPaymentUrl);
        return ccPaymentUrl;
    }

}
