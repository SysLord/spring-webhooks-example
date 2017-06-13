package de.syslord.microservices.webhooks.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import de.syslord.microservices.webhooks.utils.JsonNoGetterAutodetect;

@JsonNoGetterAutodetect
public abstract class SubscriptionEvent {

	@JsonUnwrapped
	private EventPlaceholders eventPlaceholder = new EventPlaceholders();

	public EventPlaceholders getPlaceholder() {
		return eventPlaceholder;
	}

	protected void addPlaceholder(String param, String value) {
		eventPlaceholder.add(param, value);
	}

	public String applyPlaceholder(String stringWithPlaceholders) {
		return eventPlaceholder.apply(stringWithPlaceholders);
	}

	public abstract String getName();

}
