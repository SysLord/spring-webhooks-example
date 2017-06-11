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

	@Override
	public void add(String username, Subscription subscription) throws SubscriptionException {
		if (!userSubscriptions.containsKey(username)) {
			userSubscriptions.put(username, new UserSubscriptions());
		}

		UserSubscriptions subscriptions = userSubscriptions.get(username);
		subscriptions.add(subscription);
	}

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
