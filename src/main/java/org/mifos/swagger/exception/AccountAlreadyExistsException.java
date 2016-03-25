package org.mifos.swagger.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AccountAlreadyExistsException extends WebApplicationException {

	private static final long serialVersionUID = 6817489620338221395L;

	public AccountAlreadyExistsException( final String accountNo ) {
		super(
			Response
				.status( Status.CONFLICT )
				.build()
		);
	}
}
