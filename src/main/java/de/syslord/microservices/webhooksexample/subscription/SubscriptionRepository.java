package de.syslord.microservices.webhooksexample.subscription;

import de.syslord.microservices.webhooksexample.events.SubscriptionEvent;

public interface SubscriptionRepository {

	void add(String username, Subscription subscription) throws SubscriptionException;

	void delete(String username, String id) throws SubscriptionException;

	UserSubscriptions getSubscriptionsForUser(String username);

	void fireEvent(SubscriptionEvent subscriptionEvent);

}
