package coffeeshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffeeshop.mapper.AccountMapper;
import coffeeshop.model.Account;
import coffeeshop.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public void createAccount(Account account) {
		accountMapper.insert(account);
	}
	
	@Override
	public Account findByUsername(String username) {
		return accountMapper.findByUsername(username);
	}

	@Override
	public void updatePassword(String username, String password) {

	}

	@Override
	public void updateEnabled(String username, boolean enabled) {
		
	}
}
