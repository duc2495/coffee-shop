package coffeeshop.service;

import coffeeshop.model.Account;
import coffeeshop.model.User;

public interface UserService {
	public void createUser(User user);
	
	public User findUserByUsername(String username);

	public User findUserByEmail(String email);
	
	public User findUserByToken(String token);

	public String resetToken(String email);
	
	public void sendRegistrationToken(User user);
	
	public void resendRegistrationToken(User user);

	public void updatePassword(Account account);
	
	public void activeUser(Account account);
}
