package com.linkedin.learning.webservices.rest.filter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;


@Provider
public class CustomLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private final Logger logger = Logger.getLogger("CustomLoggingFilter");

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		logger.info("Outbound message "+responseContext.getEntity());

	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		logger.info("Inbound request filter");
		BufferedInputStream buff = new BufferedInputStream(requestContext.getEntityStream());
		byte[] istream = new byte[buff.available()];
		buff.read(istream);
		String inboundBody = new String(istream,"utf-8");
		logger.info("Inbound message "+inboundBody);
		requestContext.setEntityStream(new ByteArrayInputStream(istream));
	}

}
