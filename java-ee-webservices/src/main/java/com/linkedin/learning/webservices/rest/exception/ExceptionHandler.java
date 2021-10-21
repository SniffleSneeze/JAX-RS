package com.linkedin.learning.webservices.rest.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {

		Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
		StringBuilder responseBody = new StringBuilder();
		violations.forEach(violation -> responseBody.append("Value: " + violation.getInvalidValue() + " failed validation because: "+violation.getMessage()));
		Response respondWith = Response.status(400,responseBody.toString()).type(MediaType.APPLICATION_JSON).build();
		return respondWith;
	}

}
