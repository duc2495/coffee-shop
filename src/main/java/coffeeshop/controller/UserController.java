package coffeeshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import coffeeshop.model.User;
import coffeeshop.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        return "login";
    }

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	protected String registerForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	protected String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
/*		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "register";
		}*/
		String password = user.getAccount().getPassword();
		String encodedPwd = passwordEncoder.encode(password);
		user.getAccount().setPassword(encodedPwd);

		userService.createUser(user);
		redirectAttributes.addFlashAttribute("info", "User created successfully");
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/registerConfirm", method = RequestMethod.GET)
	public String confirmRegister(Model model, @RequestParam("token") String token) {
		User user = userService.findUserByToken(token);
/*	    if (user == null) {
	        String message = "invalidToken";
	        model.addAttribute("message", message);
	        return "redirect:/badUser";
	    }*/
	    userService.activeUser(user.getAccount().getUsername());
		return "login";
	}
	
	@RequestMapping(value = "/badUser", method = RequestMethod.GET)
	public String badUser(Model model) {
		return "badUser";
	}
}
