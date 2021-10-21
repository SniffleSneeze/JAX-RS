package com.linkedin.learning.webservices.rest.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
@PreMatching
public class CORSFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		if(!isCors(requestContext)) {
			return;
		}
		
		if(isPreflight(requestContext)) {
			responseContext.getHeaders().add("Access-Control-Allow-Credentials", true);
			responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET,PUT,POST,OPTIONS,HEAD");
			responseContext.getHeaders().add("Access-Control-Allow-Headers", "*");
		}
		
		
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if(isPreflight(requestContext)) {
			requestContext.abortWith(Response.ok().build());
		}
		
	}
	
	public boolean isPreflight(ContainerRequestContext ctxt) {
		return ctxt.getHeaderString("Origin") != null && ctxt.getMethod().equalsIgnoreCase("OPTIONS");
	}
	
	public boolean isCors(ContainerRequestContext ctxt) {
		return ctxt.getHeaderString("Origin")!= null;
	}

}
