package de.syslord.microservices.webhooksexample.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("eventSecurity")
public class EventSecurity {

	public boolean hasEventPermission(String key) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication.getAuthorities().stream()
			.map(auth -> auth.getAuthority())
			.filter(auth -> auth.equals("EVENT_" + key))
			.findFirst().isPresent();
	}
}