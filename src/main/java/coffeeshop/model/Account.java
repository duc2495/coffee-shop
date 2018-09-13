package coffeeshop.model;

import lombok.Data;

@Data
public class Account {
	private String username;
	private String password;
	private String passwordConfirm;
	boolean enabled;
	private Role role;

	public Account() {
		super();
		this.enabled = false;
	}
}
