package coffeeshop.service;

import coffeeshop.model.User;

public interface UserService {
	public void createUser(User user);
	
	public User findUserByUsername(String username);

	public User findUserByEmail(String email);
	
	public User findUserByToken(String token);

	public String resetToken(String email);
	
	public void confirmRegistration(User user);

	public boolean verifyToken(String email, String token);

	public void updatePassword(String username, String password);
	
	public void activeUser(String username);
}
