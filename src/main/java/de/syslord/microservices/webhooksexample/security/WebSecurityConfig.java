package de.syslord.microservices.webhooksexample.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.syslord.microservices.webhooksexample.rest.ExampleServiceToBeCalled;

@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Use basic auth for any request
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(getUnsecuredRequestMappings()).permitAll()
			.anyRequest().fullyAuthenticated()
			.and()
			.httpBasic().and().csrf().disable();
	}

	protected String[] getUnsecuredRequestMappings() {
		return new String[] {
				ExampleServiceToBeCalled.CALLME1_ENDPOINT,
				ExampleServiceToBeCalled.STATS_ENDPOINT
		};
	}

}