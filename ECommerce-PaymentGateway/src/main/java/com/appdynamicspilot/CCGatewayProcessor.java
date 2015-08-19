package com.appdynamicspilot;

import org.apache.commons.lang.RandomStringUtils;
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

    private static String RespStr = RandomStringUtils.random(8192);


	@GET
	@Path("/ccpay")
	@Produces(MediaType.TEXT_PLAIN)
	public String processPayment() {
		Random randInteger = new Random();
		int randomNumber = randInteger.nextInt(10);

		if (randomNumber % 2 == 0) {
			return "success : " + RespStr;
		}
		else
			return "failure: " + RespStr;

	}

}
