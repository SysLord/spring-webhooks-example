package de.syslord.microservices.webhooksexample.example.security.auth;

import org.springframework.stereotype.Component;

import de.syslord.microservices.webhooksexample.events.HeartbeatEvent;

@Component
public class ExampleUserRepository {

	public UserAccount findByName(String username) {
		if ("test".equals(username)) {
			return new UserAccount("test", "test", "ROLE_USER,ROLE_SUBSCRIBER,EVENT_" + HeartbeatEvent.EVENT_NAME);
		} else if ("all".equals(username)) {
			return new UserAccount("all", "all", "ROLE_USER,ROLE_SUBSCRIBER,EVENT_" + HeartbeatEvent.EVENT_NAME + ",EVENT_LOLCATS");
		} else if ("lol".equals(username)) {
			return new UserAccount("lol", "lol", "ROLE_USER,ROLE_SUBSCRIBER,EVENT_LOLCATS");
		}

		return null;
	}

}
