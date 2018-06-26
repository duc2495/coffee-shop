package coffeeshop.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import coffeeshop.model.Account;
import coffeeshop.service.AccountService;

@Component
public class AccountValidator implements Validator {

	@Autowired
	private AccountService accountService;

	@Override
	public boolean supports(Class<?> arg0) {
		return Account.class.isAssignableFrom(arg0);

	}

	@Override
	public void validate(Object obj, Errors errors) {
		Account account = (Account) obj;

		if (accountService.findByUsername(account.getUsername()) != null) {
			errors.rejectValue("account.username", "error.username_registered", new Object[] { account.getUsername() }, "");
		}

		if (!account.getPasswordConfirm().equals(account.getPassword())) {
			errors.rejectValue("account.passwordConfirm", "error.password_conf_password_mismatch");
		}
	}

}