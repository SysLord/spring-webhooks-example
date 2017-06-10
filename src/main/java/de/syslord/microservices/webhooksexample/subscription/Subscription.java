package de.syslord.microservices.webhooksexample.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.syslord.microservices.webhooksexample.utils.JsonNoAutodetect;

@JsonNoAutodetect
public class Subscription {

	@JsonProperty("remoteAdress")
	private String remoteAdress;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	public Subscription() {
		// deserialization constructor
	}

	public Subscription(String remoteAdress, String username) {
		this.remoteAdress = remoteAdress;
		this.username = username;
	}

	public String getRemoteAdress() {
		return remoteAdress;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
