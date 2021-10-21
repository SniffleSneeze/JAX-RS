package com.linkedin.learning.webservices.rest.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.linkedin.learning.webservices.rest.exception.ExceptionHandler;

@ApplicationPath("webapi")
public class ApplicationConfig extends ResourceConfig {
	
	private final Logger LOGGER = Logger.getLogger("MyResource");
	
	public ApplicationConfig() {
		packages("com.linkedin.learning.webservices.rest","io.swagger.v3.jaxrs2.integration.resources");
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE,true);
		register(new LoggingFeature(LOGGER, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY,5000));
		property(ServerProperties.MONITORING_ENABLED, true);
		property(ServerProperties.TRACING,"ALL");
	}

}
