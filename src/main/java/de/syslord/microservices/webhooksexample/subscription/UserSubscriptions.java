package de.syslord.microservices.webhooksexample.subscription;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.syslord.microservices.webhooksexample.utils.JsonNoAutodetect;

@JsonNoAutodetect
public class UserSubscriptions {

	@JsonProperty("subscriptions")
	private Map<String, Subscription> idSubscriptions = new HashMap<>();

	public UserSubscriptions() {
		// deserialization constructor
	}

	private UserSubscriptions(Map<String, Subscription> idSubscriptions) {
		this.idSubscriptions = idSubscriptions;
	}

	public static UserSubscriptions createEmpty() {
		return new UserSubscriptions(Collections.emptyMap());
	}

	public synchronized void add(Subscription subscription) throws SubscriptionException {
		if (idSubscriptions.containsKey(subscription.getId())) {
			throw new SubscriptionException("id already used");
		}

		idSubscriptions.put(subscription.getId(), subscription);
	}

	public synchronized void delete(String id) {
		idSubscriptions.remove(id);
	}

	public List<Subscription> getSubscriptions() {
		return idSubscriptions.values().stream().collect(Collectors.toList());
	}

	public List<Subscription> getSubscriptions(String eventname) {
		return idSubscriptions.values().stream()
			.filter(s -> s.matchesEvent(eventname))
			.collect(Collectors.toList());
	}

}
