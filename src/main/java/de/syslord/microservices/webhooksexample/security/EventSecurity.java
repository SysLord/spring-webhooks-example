package de.syslord.microservices.webhooksexample.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("eventSecurity")
public class EventSecurity {

	public boolean hasEventPermission(String key) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// This seems pretty stupid. If we must use SecurityExpressionRoot then maybe put it somwhere for
		// this user? If we don't use it we need to implement hasAuthority by ourselves, right?
		// new SimpleSecurityExpressionRoot(authentication).hasAuthority("EVENT_" + key);

		return authentication.getAuthorities().stream()
			.map(auth -> auth.getAuthority())
			.filter(auth -> auth.equals("EVENT_" + key))
			.findFirst().isPresent();
	}
}