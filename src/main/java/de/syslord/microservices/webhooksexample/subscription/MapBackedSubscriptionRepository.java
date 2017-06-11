package de.syslord.microservices.webhooksexample.subscription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.syslord.microservices.webhooksexample.events.SubscriptionEvent;
import de.syslord.microservices.webhooksexample.utils.AsyncServiceCaller;

@Component
public class MapBackedSubscriptionRepository implements SubscriptionRepository {

	@Autowired
	private AsyncServiceCaller asyncServiceCaller;

	private Map<String, UserSubscriptions> userSubscriptions = new HashMap<>();

	public void add(String name, Subscription subscription) throws SubscriptionException {
		if (!userSubscriptions.containsKey(name)) {
			userSubscriptions.put(name, new UserSubscriptions());
		}

		UserSubscriptions subscriptions = userSubscriptions.get(name);
		subscriptions.add(subscription);
	}

	public UserSubscriptions getSubscriptionsForUser(String username) {
		if (userSubscriptions.containsKey(username)) {
			return userSubscriptions.get(username);
		}
		return UserSubscriptions.createEmpty();
	}

	// TODO here?
	public void fireEvent(SubscriptionEvent subscriptionEvent) {
		List<Subscription> callList = getSubscriptionsForEvent(subscriptionEvent.getName());

		// TODO log user and event infos

		callList.stream()
			.forEach(subscription -> {
				String callAddress = subscriptionEvent.applyPlaceholder(subscription.getRemoteAdress());
				asyncServiceCaller.enqueue(callAddress);
			});
	}

	private List<Subscription> getSubscriptionsForEvent(String eventname) {
		// TODO by event
		return userSubscriptions.values().stream()
			.flatMap(s -> s.getSubscriptions(eventname).stream())
			.collect(Collectors.toList());
	}

}
