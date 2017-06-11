package de.syslord.microservices.webhooksexample.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import de.syslord.microservices.webhooksexample.events.SubscriptionEvent;
import de.syslord.microservices.webhooksexample.subscription.Subscription;
import de.syslord.microservices.webhooksexample.subscription.SubscriptionException;
import de.syslord.microservices.webhooksexample.subscription.SubscriptionRepository;
import de.syslord.microservices.webhooksexample.subscription.UserSubscriptions;
import de.syslord.microservices.webhooksexample.utils.AsyncServiceCaller;

@Component
public class MapBackedSubscriptionRepository implements SubscriptionRepository {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MapBackedSubscriptionRepository.class);

	@Autowired
	private AsyncServiceCaller asyncServiceCaller;

	private Map<String, UserSubscriptions> userSubscriptions = new HashMap<>();

	@PreAuthorize("@eventSecurity.hasEventPermission(#subscription.event)")
	@Override
	public void add(String username, Subscription subscription) throws SubscriptionException {
		if (!userSubscriptions.containsKey(username)) {
			userSubscriptions.put(username, new UserSubscriptions());
		}

		UserSubscriptions subscriptions = userSubscriptions.get(username);
		subscriptions.add(subscription);
	}

	// TODO change subscription replaces it with new subscription

	@Override
	public void delete(String username, String id) {
		if (!userSubscriptions.containsKey(username)) {
			return;
		}
		UserSubscriptions subscriptions = userSubscriptions.get(username);
		subscriptions.delete(id);
	}

	@Override
	public UserSubscriptions getSubscriptionsForUser(String username) {
		if (userSubscriptions.containsKey(username)) {
			return userSubscriptions.get(username);
		}
		return UserSubscriptions.createEmpty();
	}

	@Override
	public void fireEvent(SubscriptionEvent subscriptionEvent) {
		List<Subscription> callList = getSubscriptionsForEvent(subscriptionEvent.getName());

		// TODO log user and event infos

		// Subscription is immutable, so we can pass it around
		callList.stream()
			.forEach(subscription -> {

				String address = subscriptionEvent.applyPlaceholder(subscription.getRemoteAdress());
				String body = subscriptionEvent.applyPlaceholder(subscription.getPostBody());

				ServiceCall serviceCall = new ServiceCall(
						subscription,
						subscription.getHttpMethod(), address,
						subscription.getUsername(), subscription.getPassword(),
						body);

				asyncServiceCaller.enqueue(serviceCall);
			});
	}

	private List<Subscription> getSubscriptionsForEvent(String eventname) {
		return userSubscriptions.values().stream()
			.flatMap(s -> s.getSubscriptions(eventname).stream())
			.collect(Collectors.toList());
	}

}
