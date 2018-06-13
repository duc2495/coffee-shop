package coffeeshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffeeshop.mapper.UserMapper;
import coffeeshop.model.User;
import coffeeshop.service.AccountService;
import coffeeshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AccountService accountService;

	@Override
	public User findUserByEmail(String email) {
		return userMapper.findByEmail(email);
	}

	@Override
	public User findUserByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	@Override
	public String resetPassword(String email) {
		User user = findUserByEmail(email);
		return accountService.resetPassword(user.getUsername());
	}

	@Override
	public boolean verifyPasswordResetToken(String email, String token) {
		User user = findUserByEmail(email);
		return accountService.verifyPasswordResetToken(user.getUsername(), token);
	}

	@Override
	public void updatePassword(String email, String token, String password) {
		User user = findUserByEmail(email);
		accountService.updatePassword(user.getPhoneNumber(), token, password);
	}

}
