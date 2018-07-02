package coffeeshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coffeeshop.model.Account;
import coffeeshop.repository.AccountRepository;
import coffeeshop.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	@Override
	public void createAccount(Account account) {
		accountRepository.insertAccount(account);
	}
	
	@Override
	public Account findByUsername(String username) {
		return accountRepository.findAccountByUsername(username);
	}

	@Transactional
	@Override
	public void updateAccount(Account account) {
		accountRepository.updateAccount(account);
	}
}
