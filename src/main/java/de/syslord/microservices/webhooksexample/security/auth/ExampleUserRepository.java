package de.syslord.microservices.webhooksexample.security.auth;

import org.springframework.stereotype.Component;

import de.syslord.microservices.webhooksexample.events.Heartbeat;

@Component
public class ExampleUserRepository {

	public UserAccount findByName(String username) {
		if ("test".equals(username)) {
			return new UserAccount("test", "test", "ROLE_USER,ROLE_SUBSCRIBER,EVENT_" + Heartbeat.EVENT_NAME);
		} else if ("all".equals(username)) {
			return new UserAccount("all", "all", "ROLE_USER,ROLE_SUBSCRIBER,EVENT_" + Heartbeat.EVENT_NAME + ",EVENT_LOLCATS");
		} else if ("lol".equals(username)) {
			return new UserAccount("lol", "lol", "ROLE_USER,ROLE_SUBSCRIBER,EVENT_LOLCATS");
		}

		return null;
	}

}
