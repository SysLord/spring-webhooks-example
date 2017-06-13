package de.syslord.microservices.webhooks.utils;

import org.springframework.http.HttpEntity;

import de.syslord.microservices.webhooks.subscription.SubscriptionCallMethod;
import de.syslord.microservices.webhooks.utils.HttpEntityBuilder;

public class ServiceCall {

	private SubscriptionCallMethod httpMethod;

	private String address;

	private String username;

	private String password;

	private String body;

	public ServiceCall(SubscriptionCallMethod httpMethod, String address,
			String username, String password,
			String body) {
		this.httpMethod = httpMethod;
		this.address = address;
		this.username = username;
		this.password = password;
		this.body = body;
	}

	public SubscriptionCallMethod getHttpMethod() {
		return httpMethod;
	}

	public String getAddress() {
		return address;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getBody() {
		return body;
	}

	public boolean hasBasicAuth() {
		return username != null && !username.isEmpty() || password != null && !password.isEmpty();
	}

	public HttpEntity<?> buildHttpEntity() {
		HttpEntityBuilder entityBuilder = HttpEntityBuilder.create();

		if (hasBasicAuth()) {
			entityBuilder = entityBuilder.withBasicAuth(username, password);
		}

		if (body != null && !body.isEmpty()) {
			entityBuilder = entityBuilder.withBody(body);
		}

		return entityBuilder.build();
	}

}
