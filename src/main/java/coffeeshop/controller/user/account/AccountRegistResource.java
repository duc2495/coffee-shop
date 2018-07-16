package coffeeshop.controller.user.account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountRegistResource {
	@NotBlank
	@Size(max = 20)
	private String username;
	@NotNull
	@Size(min = 6, max = 32)
	private String password;
	@NotNull
	@Size(min = 6, max = 32)
	private String passwordConfirm;

}
