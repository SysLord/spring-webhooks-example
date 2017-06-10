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

	public UserSubscriptions getSubscriptions(String name) {
		if (userSubscriptions.containsKey(name)) {
			return userSubscriptions.get(name);
		}
		return UserSubscriptions.createEmpty();
	}

	public void fireEvent(SubscriptionEvent subscriptionEvent) {
		List<Subscription> callList = getSubscriptions(subscriptionEvent.getClass());

		// TODO log user and event infos

		callList.stream()
			.forEach(subscription -> {

				String callAddress = subscriptionEvent.applyPlaceholder(subscription.getRemoteAdress());
				asyncServiceCaller.enqueue(callAddress);

			});
	}

	private List<Subscription> getSubscriptions(Class<? extends SubscriptionEvent> clazz) {
		// TODO by event
		return userSubscriptions.values().stream()
			.flatMap(s -> s.getSubscriptions().stream())
			.collect(Collectors.toList());
	}

}
