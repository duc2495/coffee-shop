package coffeeshop.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import coffeeshop.model.Account;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	private Account account;

	public AuthenticatedUser(Account account) {
		super(account.getUsername(), account.getPassword(), account.isEnabled(), true, true, true,
				getAuthorities(account));
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Account account) {
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(account.getRole().getRoleName());
		return authorities;
	}
}
