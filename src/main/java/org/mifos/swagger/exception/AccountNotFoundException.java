package org.mifos.swagger.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AccountNotFoundException extends WebApplicationException {

private static final long serialVersionUID = -2894269137259898072L;
	
	public AccountNotFoundException( final String accountNo ) {
		super(
			Response
				.status( Status.NOT_FOUND )
				.build()
		);
	}
}
