package de.syslord.microservices.webhooksexample.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.syslord.microservices.webhooksexample.utils.JsonNoAutodetect;

@JsonNoAutodetect
public class Subscription {

	// TODO ...
	@JsonProperty("remoteAdress")
	private String remoteAdress;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	@JsonProperty("event")
	private String event;

	public Subscription() {
		// deserialization constructor
	}

	public Subscription(String remoteAdress) {
		this.remoteAdress = remoteAdress;
	}

	public String getRemoteAdress() {
		return remoteAdress;
	}

	public boolean matchesEvent(String eventname) {
		return event.equals(eventname);
	}

}
