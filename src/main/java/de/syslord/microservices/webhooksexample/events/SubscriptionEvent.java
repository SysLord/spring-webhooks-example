package de.syslord.microservices.webhooksexample.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.syslord.microservices.webhooksexample.utils.JsonNoAutodetect;

@JsonNoAutodetect
public abstract class SubscriptionEvent {

	@JsonProperty("placeholderValues")
	private EventPlaceholders eventPlaceholder = new EventPlaceholders();

	public EventPlaceholders getPlaceholder() {
		return eventPlaceholder;
	}

	protected void addPlaceholder(String param, String value) {
		eventPlaceholder.add(param, value);
	}

	public String applyPlaceholder(String remoteAdress) {
		return eventPlaceholder.apply(remoteAdress);
	}

	public abstract String getName();

}
