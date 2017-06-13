package de.syslord.microservices.webhooks.subscription;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "")
public class SubscriptionException extends Exception {

	private static final long serialVersionUID = -989484894351832971L;

	public SubscriptionException(String message) {
		super(message);
	}

}
