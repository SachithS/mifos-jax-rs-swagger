package org.mifos.swagger.jaxrs;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.mifos.swagger.domain.Account;
import org.mifos.swagger.service.AccountService;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * @author Sachith Senarathne
 * 
 * Defining all the routes for application and swagger 
 * definition parameters with the values
 *
 */
@Path( "/account" ) 
@Api( value = "/account", description = "Manage account" )
public class AccountRestController {

	@Inject
	private AccountService accountService;

	@Produces({ MediaType.APPLICATION_JSON })
	@GET
	@ApiOperation(value = "List all accounts", notes = "List all accounts", response = Account.class, responseContainer = "List")
	public Collection<Account> getAccount(
			@ApiParam(value = "Page you want to fetch", required = true) @QueryParam("page") @DefaultValue("1") final int page) {
		return accountService.getAccounts(page, 5);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{accno}")
	@GET
	@ApiOperation(value = "Find account by account number", notes = "Find account by account number", response = Account.class)
	@ApiResponses({ @ApiResponse(code = 404, message = "Person with such e-mail doesn't exists") })
	public Account getAccount(
			@ApiParam(value = "Account number to search", required = true) @PathParam("accno") final String accno) {
		return accountService.getByAccountNumber(accno);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@ApiOperation(value = "Create new account", notes = "Create new account")
	@ApiResponses({ @ApiResponse(code = 201, message = "Account created successfully"),
			@ApiResponse(code = 409, message = "Account with such account number already exists") })
	public Response addAccount(@Context final UriInfo uriInfo,
			@ApiParam(value = "Account No", required = true) @FormParam("accno") final String accno,
			@ApiParam(value = "Account Holder", required = true) @FormParam("holder") final String holder,
			@ApiParam(value = "Branch", required = true) @FormParam("branch") final String branch) {

		accountService.addAccount(accno, holder, branch);
		return Response.created(uriInfo.getRequestUriBuilder().path(accno).build()).build();
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{accno}")
	@PUT
	@ApiOperation(value = "Update existing account", notes = "Update existing account", response = Account.class)
	@ApiResponses({ @ApiResponse(code = 404, message = "Account with such account number doesn't exists") })
	public Account updateAccount(
			@ApiParam(value = "Account Number", required = true) @PathParam("accno") final String accno,
			@ApiParam(value = "Account Holder", required = false) @FormParam("firstName") final String holder,
			@ApiParam(value = "Branch ", required = false) @FormParam("lastName") final String branch) {

		final Account account = accountService.getByAccountNumber(accno);

		if (accno != null) {
			account.setAccountNumber(accno);
		}

		if (holder != null) {
			account.setAccountHolder(holder);
		}

		return account;
	}

	@Path("/{accno}")
	@DELETE
	@ApiOperation(value = "Delete existing account", notes = "Delete existing account", response = Account.class)
	@ApiResponses({ @ApiResponse(code = 404, message = "Account with such account number not exists") })
	public Response deletePerson(@ApiParam(value = "E-Mail", required = true) @PathParam("accno") final String accno) {
		accountService.removeAccount(accno);
		return Response.ok().build();
	}
}
