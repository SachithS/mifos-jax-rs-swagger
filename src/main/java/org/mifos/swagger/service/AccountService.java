package org.mifos.swagger.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.mifos.swagger.domain.Account;
import org.mifos.swagger.exception.AccountAlreadyExistsException;
import org.mifos.swagger.exception.AccountNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Sachith Senarathne
 * @
 * Class will responsible for managing the operations related to the Account class
 *
 */

@Service
public class AccountService {

	// Map to store accounts on the fly
	private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<String, Account>();

	//Get account as the set of numbers needed by the client side
	public Collection<Account> getAccounts(int page, int pageSize) {
		
		//ArraList of set of accounts
		final Collection<Account> slice = new ArrayList<Account>(pageSize);

		// getting the relevant set of accounts from the map
		final Iterator<Account> iterator = accounts.values().iterator();
		for (int i = 0; slice.size() < pageSize && iterator.hasNext();) {
			if (++i > ((page - 1) * pageSize)) {
				slice.add(iterator.next());
			}
		}

		return slice;
	}

	
	public Account getByAccountNumber(final String accountNo) {
		//get account by account number
		final Account account = accounts.get(accountNo);

		if (account == null) {
			//throwing the custom exception created when account is not found
			throw new AccountNotFoundException(accountNo);
		}

		return account;
	}

	
	/**
	 * @param accountNo
	 * @param accountHolder
	 * @param branch
	 * @return account object if found
	 * 
	 * Add account to the map
	 */
	public Account addAccount(final String accountNo, final String accountHolder, final String branch) {
		final Account account = new Account(accountNo);
		account.setAccountNumber(accountHolder);
		account.setBranch(branch);
		
		if (accounts.putIfAbsent(accountNo, account) != null) {
			//throwing the custom error if account is exist
			throw new AccountAlreadyExistsException(accountNo);
		}

		return account;
	}

	/**
	 * @param accountNo
	 * Remove Account from the map
	 */
	public void removeAccount(final String accountNo) {
		
		if (accounts.remove(accountNo) == null) {
			//throwing the exception if account is not found
			throw new AccountNotFoundException(accountNo);
		}
	}
}
