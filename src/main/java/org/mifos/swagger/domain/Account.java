package org.mifos.swagger.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Sachith Senarathne
 * 
 * This will act as the domain object of the application. will hold the
 * details about an account
 */

@ApiModel( value = "Account", description = "Account model representation" )
public class Account {

	// Adding api model properties for the attributes with display values
	@ApiModelProperty(value = "Account number", required = true)
	private String accountNumber;
	@ApiModelProperty(value = "Account holder's name", required = true)
	private String accountHolder;
	@ApiModelProperty(value = "Name of the account's branch", required = true)
	private String branch;

	// default constructor for account
	public Account() {
		// TODO Auto-generated constructor stub
	}

	// overloaded constructor with account number
	public Account(final String accountNo) {
		this.accountNumber = accountNo;
	}

	// overloaded constructor with account number
	public Account(final String accHolder, final String branch) {
		this.accountHolder = accHolder;
		this.branch = branch;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

}
