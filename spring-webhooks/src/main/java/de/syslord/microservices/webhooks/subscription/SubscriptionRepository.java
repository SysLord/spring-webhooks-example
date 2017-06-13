package de.syslord.microservices.webhooks.subscription;

import org.springframework.security.access.prepost.PreAuthorize;

import de.syslord.microservices.webhooks.events.SubscriptionEvent;

public interface SubscriptionRepository {

	@PreAuthorize("hasRole('ROLE_SUBSCRIBER') && @eventSecurity.hasEventPermission(#subscription.event)")
	void add(String username, Subscription subscription) throws SubscriptionException;

	@PreAuthorize("hasRole('ROLE_SUBSCRIBER') && @eventSecurity.hasEventPermission(#subscription.event)")
	void patch(String username, Subscription subscription) throws SubscriptionException;

	@PreAuthorize("hasRole('ROLE_SUBSCRIBER')")
	void delete(String username, String id);

	@PreAuthorize("hasRole('ROLE_SUBSCRIBER')")
	UserSubscriptions getSubscriptionsForUser(String username);

	void fireEvent(SubscriptionEvent subscriptionEvent);

}
