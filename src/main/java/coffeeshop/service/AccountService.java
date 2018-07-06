package coffeeshop.service;

import coffeeshop.model.Account;

public interface AccountService {
	public void createAccount(Account account);
	
	public Account findByUsername(String username);

	public void updateAccount(Account account);
}
