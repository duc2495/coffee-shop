package coffeeshop.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	@Override
	public void createUser(User user) {
		// Create new token and expire after 24h
		String token = UUID.randomUUID().toString();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);

		user.setExpiryDate(cal.getTime());
		user.setToken(token);
		Account account = user.getAccount();
		Role role = new Role("ROLE_CUSTOMER");
		account.setRole(role);
		account.setEnabled(false);
		accountService.createAccount(account);
		userMapper.insertUser(user);
		this.sendRegistrationToken(user);
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

	@Transactional
	@Override
	public String resetToken(String email) {
		// Create new token and expire after 24h
		String token = UUID.randomUUID().toString();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date date = cal.getTime();
		userMapper.resetToken(email, token, date);
		return token;
	}

	@Override
	public void updatePassword(String username, String password) {
		accountService.updatePassword(username, password);
	}

	@Override
	public void sendRegistrationToken(User user) {
		// Send email confirm
		String subject = "Registration Confirmation";
		String content = "Message: " + "http://localhost:8080" + "/registerConfirm?token=" + user.getToken();
		emailService.sendEmail(user.getEmail(), subject, content);
	}
	
	@Override
	public void resendRegistrationToken(User user) {
		// Send email confirm
		String subject = "Resend Registration Token";
		String content = "Message: " + "http://localhost:8080" + "/registerConfirm?token=" + user.getToken();
		emailService.sendEmail(user.getEmail(), subject, content);
	}

	@Override
	public void activeUser(String username) {
		accountService.updateEnabled(username, true);
	}

}
