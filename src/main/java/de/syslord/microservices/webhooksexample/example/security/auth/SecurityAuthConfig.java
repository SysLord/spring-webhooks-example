package de.syslord.microservices.webhooksexample.example.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.google.common.annotations.VisibleForTesting;

import de.syslord.microservices.webhooksexample.example.ExampleUserRepository;

@Configuration
public class SecurityAuthConfig extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private ExampleUserRepository userRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	UserDetailsService userDetailsService() {
		return username -> load(username);
	}

	@VisibleForTesting
	protected UserDetails load(String username) {
		UserAccount userAccount = userRepository.findByName(username);

		return SecurityUserFactory.createUserOrNull(userAccount);
	}

}
