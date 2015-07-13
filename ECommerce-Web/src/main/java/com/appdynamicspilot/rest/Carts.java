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

package com.appdynamicspilot.rest;

import com.appdynamics.apm.appagent.api.AgentDelegate;
import com.appdynamics.apm.appagent.api.IMetricAndEventReporter;
import com.appdynamicspilot.model.Cart;
import com.appdynamicspilot.model.Item;
import com.appdynamicspilot.model.User;
import com.appdynamicspilot.service.CartService;
import com.appdynamicspilot.service.UserService;
import com.appdynamicspilot.util.SpringContext;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/cart")
public class Carts {
    private static final Logger log = Logger.getLogger(Carts.class.getName());
    public static final int TEN_SECONDS = 10;
    public static final int TITLE_TO_CAUSE_SLOWDOWN = 13;
    public static final int ONE_SECOND = 1;

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response saveItemInCart(@Context HttpServletRequest req, @Context HttpServletResponse response, @PathParam("id") Long id) {

        String sessionId = req.getHeader("JSESSIONID");
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
        }
        Item item = getCartService().getItemPersistence().getItemByID(id);
        User user = (User) req.getSession(true).getAttribute("USER");

        if (user == null) {
            String username = req.getHeader("USERNAME");
            user = getUserService().getMemberByLoginName(username);
        }
        cart.setUser(user);
        cart.addItem(item);
        //trigger the mdic
        cart.getCartTotal();
        cart.setUser(user);
        getCartService().saveItemInCart(cart);
        session.setAttribute("CART", cart);
        response.setHeader("cart-size", String.valueOf(getCartService().getCartSize(user.getId())));
        response.setHeader("cart-total", Double.toString(cart.getCartTotal()));

        return Response.noContent().build();

    }



    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<ShoppingCartItem> getAllItems(@Context HttpServletRequest req) {
        Cart cart = (Cart) req.getSession().getAttribute("CART");
        List<Item> cartItems = null;
        if (cart != null) {
            cartItems = getCartService().getAllCartItems(cart.getId());
        } else {

        }
        ShoppingCart shoppingCart = new ShoppingCart();
        for (Item cartEntry : cartItems) {
            ShoppingCartItem item = new ShoppingCartItem();
            item.setId(String.valueOf(cartEntry.getId()));
            item.setImagePath(cartEntry.getImagePath());
            item.setTitle(cartEntry.getTitle());
            item.setItemId(String.valueOf(cartEntry.getId()));
            item.setPrice(cartEntry.getPrice());
            shoppingCart.addItem(item);
        }

        return shoppingCart.getAllItems();
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteItemInCart(@Context HttpServletRequest req, @Context HttpServletResponse response, @PathParam("id") Long id) {
        String username = req.getHeader("username");
        if (username != null) {
            getCartService().deleteItemInCart(username,id);
        }

        return Response.noContent().build();
    }

    @Path("/checkout/")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.TEXT_PLAIN)
    public String checkoutCartItem(@Context HttpServletRequest req, @FormParam("itemId") List<String> idList,
                                   @FormParam("quantity") List<String> qList, @FormParam("emailId") String emailId) {
        try {

            List<Long> orderIdList = new ArrayList<Long>();
            boolean outOfStock = false;
            String orderIds = "";
            for (int i = 0; i < idList.size(); i++) {
                Long orderId = getCartService().checkOut(Long.parseLong(idList.get(i)), Integer.parseInt(qList.get(i)));

                if (i != 0) {
                    orderIds = ", " + orderIds;
                }
                if (orderId == 0) {
                    outOfStock = true;
                }
                orderIdList.add(orderId);

                //TODO See if this is used for any messaging
    /*			OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrderId(orderId);
				orderDetail.setId(cart.getItem().getId());
				orderDetail.setTitle(cart.getItem().getTitle());
	*/

            }
            if (orderIdList.size() > 0 && !outOfStock) {
                return "Order ID(s) for your order(s) : " + orderIds;

            } else {
                return
                        "Order not created as one or more items in your cart were out of stock";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Error occured processing checkout";
    }

    @Path("/co")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkout(@Context HttpServletRequest req) {
        Cart cart = (Cart) req.getSession(true).getAttribute("CART");
        User user = (User) req.getSession(true).getAttribute("USER");

        if (user == null) {
            String username = req.getHeader("USERNAME");
            if (username == null) {
                return "User not logged in. Nothing to checkout.";
            } else {
                user = getUserService().getMemberByLoginName(username);
            }
        }
        if (cart == null) {
            return "Nothing In cart to checkout.";
        }

        List<Item> items = cart.getItems();
        List<Long> orderIdList = new ArrayList<Long>();
        String orderIds = "";
        boolean outOfStock = false;
        try {
            for (Item item : items) {
                Long orderId = getCartService().checkOut(item.getId(), 1);
                if (item.getId() != 0) {
                    orderIds = ", " + orderId;
                }
                if (orderId == 0) {
                    outOfStock = true;
                }
                orderIdList.add(orderId);
            }

            if (orderIdList.size() > 0 && !outOfStock) {
                return "Total amount is $" + cart.getCartTotal() + " Order ID(s) for your order(s) : " + orderIds;
            } else {
                return "Order not created as one or more items in your cart were out of stock. Total was $" + cart.getCartTotal();
            }
        } catch (Exception ex) {
            log.fatal(ex);
        }
        return "Error Processing Checkout";
    }

    public CartService getCartService() {
        return (CartService) SpringContext.getBean("cartService");
    }

    public UserService getUserService() {
        return (UserService) SpringContext.getBean("userService");
    }

}
