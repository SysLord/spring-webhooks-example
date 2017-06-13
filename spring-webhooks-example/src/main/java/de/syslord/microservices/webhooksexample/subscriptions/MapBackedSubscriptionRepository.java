package de.syslord.microservices.webhooksexample.subscriptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.syslord.microservices.webhooks.events.SubscriptionEvent;
import de.syslord.microservices.webhooks.subscription.Subscription;
import de.syslord.microservices.webhooks.subscription.SubscriptionException;
import de.syslord.microservices.webhooks.subscription.SubscriptionRepository;
import de.syslord.microservices.webhooks.subscription.UserSubscriptions;
import de.syslord.microservices.webhooks.utils.AsyncServiceCaller;
import de.syslord.microservices.webhooks.utils.ServiceCall;

@Component
public class MapBackedSubscriptionRepository implements SubscriptionRepository {

	private static final Logger logger = LoggerFactory.getLogger(MapBackedSubscriptionRepository.class);

	@Autowired
	private AsyncServiceCaller asyncServiceCaller;

	private Map<String, UserSubscriptions> userSubscriptions = new HashMap<>();

	// TODO C.Helmer b27622 13.06.2017
	// @PreAuthorize("@eventSecurity.hasEventPermission(#subscription.event)")
	@Override
	public void add(String username, Subscription subscription) throws SubscriptionException {
		assertSubscriptionsInitialized(username);

		UserSubscriptions subscriptions = userSubscriptions.get(username);

		subscriptions.add(subscription.withOwner(username));
	}

	// @PreAuthorize("@eventSecurity.hasEventPermission(#subscription.event)")
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
	public void fireEvent(SubscriptionEvent subscriptionEvent) {
		List<Subscription> callList = getSubscriptionsForEvent(subscriptionEvent.getName());

		String owners = callList.stream()
			.map(s -> s.getOwner())
			.distinct()
			.collect(Collectors.joining(", "));

		logger.info("fire event {}: {} calls for {}", subscriptionEvent.getName(), callList.size(), owners);

		callList.stream()
			.map(subscription -> {
				String address = subscriptionEvent.applyPlaceholder(subscription.getRemoteAdress());
				String body = subscriptionEvent.applyPlaceholder(subscription.getPostBody());

				ServiceCall serviceCall = new ServiceCall(
						subscription.getHttpMethod(), address,
						subscription.getUsername(), subscription.getPassword(),
						body);
				return serviceCall;
			})
			.forEach(serviceCall -> asyncServiceCaller.enqueue(serviceCall));
	}

	private List<Subscription> getSubscriptionsForEvent(String eventname) {
		return userSubscriptions.values().stream()
			.flatMap(s -> s.getSubscriptions(eventname).stream())
			.collect(Collectors.toList());
	}

}
