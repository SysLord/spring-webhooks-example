package de.syslord.microservices.webhooksexample.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import de.syslord.microservices.webhooksexample.utils.JsonNoAutodetect;

@JsonNoAutodetect
@JsonPropertyOrder(value = { "id", "event", "remoteAddress" })
public class Subscription {

	// TODO ...

	@JsonProperty("id")
	private String id;

	@JsonProperty("event")
	private String event;

	@JsonProperty("remoteAddress")
	private String remoteAddress;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	public Subscription() {
		// deserialization constructor
	}

	public Subscription(String remoteAdress) {
		this.remoteAddress = remoteAdress;
	}

	public String getRemoteAdress() {
		return remoteAddress;
	}

	public boolean matchesEvent(String eventname) {
		return event.equals(eventname);
	}

	public String getId() {
		return id;
	}

	public String getEvent() {
		return event;
	}

}
