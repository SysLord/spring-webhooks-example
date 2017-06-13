package de.syslord.microservices.webhooksexample.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.syslord.microservices.webhooks.events.Events;
import de.syslord.microservices.webhooks.subscription.Subscription;
import de.syslord.microservices.webhooks.subscription.SubscriptionException;
import de.syslord.microservices.webhooks.subscription.SubscriptionRepository;
import de.syslord.microservices.webhooks.subscription.UserSubscriptions;

@RestController
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private Events events;

	@PutMapping(
			path = "/subscription",
			name = "/subscription",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<UserSubscriptions> createSubscribe(
			@RequestBody Subscription subscription,
			Principal principal) throws SubscriptionException {

		String username = principal.getName();
		subscriptionRepository.add(username, subscription);

		return ResponseEntity.ok().body(subscriptionRepository.getSubscriptionsForUser(username));
	}

	@PatchMapping(
			path = "/subscription",
			name = "/subscription",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<UserSubscriptions> patchSubscription(
			@RequestBody Subscription subscription,
			Principal principal) throws SubscriptionException {

		String username = principal.getName();
		subscriptionRepository.patch(username, subscription);

		return ResponseEntity.ok().body(subscriptionRepository.getSubscriptionsForUser(username));
	}

	@DeleteMapping(
			path = "/subscription/{id}",
			name = "/subscription/{id}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<UserSubscriptions> deleteSubscription(
			@PathVariable String id,
			Principal principal) {

		String username = principal.getName();
		subscriptionRepository.delete(username, id);

		return ResponseEntity.ok().body(subscriptionRepository.getSubscriptionsForUser(username));
	}

	@GetMapping(
			path = "/subscription",
			name = "/subscription",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<UserSubscriptions> getSubscriptions(Principal principal) {

		UserSubscriptions subscriptionsForUser = subscriptionRepository.getSubscriptionsForUser(principal.getName());
		return ResponseEntity.ok().body(subscriptionsForUser);
	}

	@GetMapping(
			path = "/events",
			name = "/events",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getPossibleEvents() {

		return ResponseEntity.ok().body(events.getExamples());
	}

}
