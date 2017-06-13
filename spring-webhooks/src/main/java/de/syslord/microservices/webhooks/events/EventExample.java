package de.syslord.microservices.webhooks.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.syslord.microservices.webhooks.utils.JsonNoGetterAutodetect;

@JsonNoGetterAutodetect
public class EventExample {

	@JsonProperty("eventname")
	private String eventname;

	@JsonProperty("exampleValues")
	private SubscriptionEvent exampleValues;

	public EventExample(String eventname, SubscriptionEvent exampleValues) {
		this.eventname = eventname;
		this.exampleValues = exampleValues;
	}

	public String getEventname() {
		return eventname;
	}

}