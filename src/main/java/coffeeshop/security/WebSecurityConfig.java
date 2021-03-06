package coffeeshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService customUserDetailsService;

	@Autowired
	private CustomAuthenticationFailureHandler authenticationFailureHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**", "/webjars/**", "/assets/**").permitAll()
				//.antMatchers("/**", "/home", "/register", "/forgotPwd", "/resetPwd", "/order").permitAll().anyRequest()
				.antMatchers("/admin", "/admin/**")
				.authenticated().and().formLogin().loginPage("/admin/login").defaultSuccessUrl("/admin")
				.failureHandler(authenticationFailureHandler).permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
				// .logoutUrl("/logout")
				.permitAll().and().exceptionHandling().accessDeniedPage("/403").and().csrf();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("admin")
		.password(passwordEncoder().encode("admin")).roles("ADMIN");
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
}
