package coffeeshop.service;

import coffeeshop.model.Account;

public interface AccountService {
	public Account findByUsername(String username);

	public String resetPassword(String username);

	public boolean verifyPasswordResetToken(String username, String token);

	public void updatePassword(String username, String token, String password);
}
