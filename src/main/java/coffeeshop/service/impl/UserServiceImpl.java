package coffeeshop.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import coffeeshop.mapper.UserMapper;
import coffeeshop.model.Account;
import coffeeshop.model.Role;
import coffeeshop.model.User;
import coffeeshop.service.AccountService;
import coffeeshop.service.EmailService;
import coffeeshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private EmailService emailService;

	@Override
	public void createUser(User user) {
		String token = UUID.randomUUID().toString();
		user.setToken(token);
		Account account = user.getAccount();
		Role role = new Role("ROLE_CUSTOMER");
		account.setRole(role);
		account.setEnabled(false);
		accountService.createAccount(account);
		userMapper.create(user);
		confirmRegistration(user);
	}

	@Override
	public User findUserByEmail(String email) {
		return userMapper.findByEmail(email);
	}

	@Override
	public User findUserByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	@Override
	public User findUserByToken(String token) {
		return userMapper.findByToken(token);
	}

	@Override
	public String resetToken(String email) {
		String token = UUID.randomUUID().toString();
		userMapper.updateToken(email, token);
		return token;
	}

	@Override
	public boolean verifyToken(String email, String token) {
		User user = findUserByEmail(email);
		if (user == null) {
			System.out.println("User not exist");
			return false;
		}
		if (!StringUtils.hasText(token) || !token.equals(user.getToken())) {
			System.out.println("Invalid Token: '" + user.getToken() + "'.");
			return false;
		}
		return true;
	}

	@Override
	public void updatePassword(String username, String password) {
		accountService.updatePassword(username, password);
	}

	@Override
	public void confirmRegistration(User user) {
		// Send email confirm
		String subject = "Registration Confirmation";
		String content = "Message: " + "http://localhost:8080" + "/registerConfirm?token=" + user.getToken();
		emailService.sendEmail(user.getEmail(), subject, content);
	}

	@Override
	public void activeUser(String username) {
		accountService.updateEnabled(username, true);
	}

}
