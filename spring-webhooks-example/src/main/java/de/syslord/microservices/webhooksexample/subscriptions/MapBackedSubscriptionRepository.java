package de.syslord.microservices.webhooksexample.subscriptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.syslord.microservices.webhooks.subscription.Subscription;
import de.syslord.microservices.webhooks.subscription.SubscriptionException;
import de.syslord.microservices.webhooks.subscription.SubscriptionRepository;
import de.syslord.microservices.webhooks.subscription.UserSubscriptions;

@Component
public class MapBackedSubscriptionRepository extends SubscriptionRepository {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MapBackedSubscriptionRepository.class);

	private Map<String, UserSubscriptions> userSubscriptions = new HashMap<>();

	@Override
	public void add(String username, Subscription subscription) throws SubscriptionException {
		assertSubscriptionsInitialized(username);

		UserSubscriptions subscriptions = userSubscriptions.get(username);

		subscriptions.add(subscription.withOwner(username));
	}

	@Override
	public void patch(String username, Subscription subscription) throws SubscriptionException {
		assertSubscriptionsInitialized(username);

		UserSubscriptions subscriptions = userSubscriptions.get(username);
		subscriptions.patch(subscription.withOwner(username));
	}

	@Override
	public void delete(String username, String id) {
		if (!userSubscriptions.containsKey(username)) {
			return;
		}
		UserSubscriptions subscriptions = userSubscriptions.get(username);
		subscriptions.delete(id);
	}

	private void assertSubscriptionsInitialized(String username) {
		if (!userSubscriptions.containsKey(username)) {
			userSubscriptions.put(username, new UserSubscriptions());
		}
	}

	@Override
	public UserSubscriptions getSubscriptionsForUser(String username) {
		if (userSubscriptions.containsKey(username)) {
			return userSubscriptions.get(username);
		}
		return UserSubscriptions.createEmpty();
	}

	@Override
	protected List<Subscription> getSubscriptionsForEvent(String eventname) {
		return userSubscriptions.values().stream()
			.flatMap(s -> s.getSubscriptions(eventname).stream())
			.collect(Collectors.toList());
	}

}
