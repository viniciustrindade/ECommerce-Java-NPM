package com.appdynamicspilot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

/**
 * Created by shiv.loka on 7/7/2015.
 */

@Path("/webpay")
public class CCGatewayProcessor {

	private static final Logger logger = LoggerFactory
			.getLogger(CCGatewayProcessor.class);


	@GET
	@Path("/ccpay")
	@Produces(MediaType.TEXT_PLAIN)
	public String processPayment() {
		Random randInteger = new Random();
		int randomNumber = randInteger.nextInt(10);

		if (randomNumber % 2 == 0) {
			return "success";
		}
		else
			return "failure";

	}

}
