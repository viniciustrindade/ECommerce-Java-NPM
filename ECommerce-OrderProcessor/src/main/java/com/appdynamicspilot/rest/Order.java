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

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;

import javax.ws.rs.client.Invocation; // Added by HN

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;

@Path("/rest")
public class Order {
    private static final Logger log = Logger.getLogger(Order.class);

    private Client client = null;
    private WebTarget webTarget = null;
    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test(@Context HttpServletRequest req) {
        String username = req.getHeader("USERNAME");
        client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(UriBuilder.fromUri(GetConfigFiles()).build());

        Invocation.Builder iB = webTarget.request();
        iB.header("Connection","close");

        Response ccResponse = iB.accept(MediaType.TEXT_PLAIN).get(Response.class);

        String clientResponse = ccResponse.readEntity(String.class);
        log.info(clientResponse + " from order processor rest");
        return username;
    }

    private String GetConfigFiles() {
        GetConfigProperties properties = new GetConfigProperties();
        try {
            return properties.getOrderUrl();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
