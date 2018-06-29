package coffeeshop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import coffeeshop.config.MyLogger;
import coffeeshop.security.AuthenticatedUser;

public abstract class BaseController {
	protected final MyLogger logger = MyLogger.getLogger(getClass());

	@ModelAttribute("authenticatedUser")
	public AuthenticatedUser authenticatedUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		return authenticatedUser;
	}

	public static AuthenticatedUser getCurrentUser() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof AuthenticatedUser) {
			return ((AuthenticatedUser) principal);
		}
		
		return null;
	}

	public static boolean isLoggedIn() {
		return getCurrentUser() != null;
	}

}
