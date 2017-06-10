package de.syslord.microservices.webhooksexample.subscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.syslord.microservices.webhooksexample.utils.JsonNoAutodetect;

@JsonNoAutodetect
public class UserSubscriptions {

	@JsonProperty("subscriptions")
	private List<Subscription> subscriptions = new ArrayList<>();

	public UserSubscriptions() {
		// deserialization constructor
	}

	public UserSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public static UserSubscriptions createEmpty() {
		return new UserSubscriptions(Collections.emptyList());
	}

	public void add(Subscription subscription) {
		// TODO throw if matches previous
		subscriptions.add(subscription);
	}

	public List<Subscription> getSubscriptions() {
		return ImmutableList.copyOf(subscriptions);
	}

}
