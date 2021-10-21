package com.linkedin.learning.webservices.rest;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;


@Path("jwt")
public class JWTResource {
	
	@Context SecurityContext securityContext;
	
	private final Logger logger = Logger.getLogger("JWTResource");
	
	
	@Path("/generate-jwt")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String generateJwt() {
		return generate(securityContext.getUserPrincipal().getName());
		
	}
	
	@Path("validate-jwt")
	@POST
	public void validateJwt(String jwt) {
		validate(jwt);
	}
	
	private String generate(String userName) {
		String fixedId = "aGeneratedStringID";
		String clearTextPassword = "this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrasethis-is-a-passphrase-this-is-a-passphrase";
		byte[] byteBasedPassPhrase = DatatypeConverter.parseBase64Binary(clearTextPassword);
		
		Key signatureKey = new SecretKeySpec(byteBasedPassPhrase,"HMACSHA256");
		
		JwtBuilder builder = Jwts.builder().setId(fixedId)
								 .setIssuedAt(Date.from(Instant.now()))
								 .setSubject(userName)
								 .setIssuer("java-ee-webservice")
								 .setExpiration(Date.from(Instant.now().plus(3,ChronoUnit.HOURS)))
								 .signWith(signatureKey);
		
		return builder.compact();
		
	}
	
	private void validate(String hiddenJwt) {
		String fixedId = "aGeneratedStringID";
		String clearTextPassword = "this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrasethis-is-a-passphrase-this-is-a-passphrase";
		byte[] byteBasedPassPhrase = DatatypeConverter.parseBase64Binary(clearTextPassword);
		
		Key signatureKey = new SecretKeySpec(byteBasedPassPhrase,"HMACSHA256");
		JwtParser parser = Jwts.parser();
		
		Claims claims = parser.setSigningKey(signatureKey).parseClaimsJws(hiddenJwt).getBody();
		logger.info("The subject claim value "+claims.getSubject());
		logger.info("The JWT issuer is: "+claims.getIssuer());
		
	}

}
