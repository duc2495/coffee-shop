package coffeeshop.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import coffeeshop.mapper.AccountMapper;
import coffeeshop.model.Account;
import coffeeshop.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public Account findByUsername(String username) {
		return accountMapper.findByUsername(username);
	}
	
	@Override
	public String resetPassword(String username) {
		Account account = findByUsername(username);
		if (account == null) {
			System.out.println();
		}
		String uuid = UUID.randomUUID().toString();
		account.setPasswordResetToken(uuid);
		return uuid;
	}

	@Override
	public boolean verifyPasswordResetToken(String username, String token) {
		Account account = findByUsername(username);
		if (account == null) {
			System.out.println();
		}
		if (!StringUtils.hasText(token) || !token.equals(account.getPasswordResetToken())) {
			return false;
		}
		return true;
	}

	@Override
	public void updatePassword(String username, String token, String password) {
		Account account = findByUsername(username);
		if (account == null) {
			System.out.println();
		}
		if (!StringUtils.hasText(token) || !token.equals(account.getPasswordResetToken())) {
			System.out.println();
		}
		account.setPassword(password);
		account.setPasswordResetToken(null);
	}
}
