package de.syslord.microservices.webhooks.subscription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.syslord.microservices.webhooks.utils.JsonNoGetterAutodetect;
import de.syslord.microservices.webhooks.utils.JsonPasswordSerializer;

@JsonNoGetterAutodetect
@JsonPropertyOrder(value = { "id", "event", "remoteAddress" })
public class Subscription {

	@JsonProperty("id")
	private String id;

	@JsonProperty("event")
	private String event;

	@JsonProperty("remoteAddress")
	private String remoteAddress;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	@JsonSerialize(using = JsonPasswordSerializer.class)
	private String password;

	@JsonProperty("postBody")
	private String postBody;

	@JsonIgnore
	private String owner;

	public Subscription() {
		// deserialization constructor
	}

	public Subscription withOwner(String newOwner) {
		Subscription subscription = new Subscription();
		subscription.owner = newOwner;

		subscription.event = event;
		subscription.id = id;
		subscription.remoteAddress = remoteAddress;
		subscription.username = username;
		subscription.password = password;
		subscription.postBody = postBody;
		return subscription;
	}

	public String getOwner() {
		return owner;
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

	public String getPostBody() {
		return postBody;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public SubscriptionCallMethod getHttpMethod() {
		return postBody != null && !postBody.isEmpty() ? SubscriptionCallMethod.POST : SubscriptionCallMethod.GET;
	}

}
