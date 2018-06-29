package coffeeshop.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import coffeeshop.model.User;
import coffeeshop.service.UserService;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService	userService;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return User.class.isAssignableFrom(arg0);

	}

	@Override
	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
		
		if(userService.findUserByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "error.email_registered", new Object[] { user.getEmail() }, "");
		}
	}

}
