package de.syslord.microservices.webhooksexample.security;

import java.security.Principal;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

// TODO register as default SecurityExpressionRoot
public class SimpleSecurityExpressionRoot extends SecurityExpressionRoot {

	public SimpleSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}

	/**
	 * @param principal we expect an Authentication
	 */
	public static SimpleSecurityExpressionRoot getFromPrincipal(Principal principal) {
		if (principal != null && Authentication.class.isAssignableFrom(principal.getClass())) {
			Authentication authentication = (Authentication) principal;
			return new SimpleSecurityExpressionRoot(authentication);
		}

		throw new IllegalArgumentException("Authorization typed principal was expected");
	}

	public boolean hasEvent(String key) {
		return hasAuthority("EVENT_" + key);
	}

}
