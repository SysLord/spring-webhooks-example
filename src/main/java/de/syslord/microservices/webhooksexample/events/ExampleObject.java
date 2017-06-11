package de.syslord.microservices.webhooksexample.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.syslord.microservices.webhooksexample.utils.JsonNoAutodetect;

@JsonNoAutodetect
public class ExampleObject {

	@JsonProperty("eventname")
	private String eventname;

	@JsonProperty("exampleValues")
	private SubscriptionEvent exampleValues;

	public ExampleObject(String eventname, SubscriptionEvent exampleValues) {
		this.eventname = eventname;
		this.exampleValues = exampleValues;
	}

	public String getEventname() {
		return eventname;
	}

}