package coffeeshop.service;

import coffeeshop.model.User;

public interface UserService {
	User findUserByUsername(String username);

	User findUserByEmail(String email);

	public String resetPassword(String email);

	public boolean verifyPasswordResetToken(String email, String token);

	public void updatePassword(String email, String token, String password);
}
