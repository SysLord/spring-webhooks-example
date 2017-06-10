package de.syslord.microservices.webhooksexample.security.auth;

import org.springframework.stereotype.Component;

@Component
public class ExampleUserRepository {

	public UserAccount findByName(String username) {
		if ("test".equals(username)) {
			return new UserAccount("test", "test", "ROLE_USER,ROLE_SUBSCRIBER");
		}
		return null;
	}

}
