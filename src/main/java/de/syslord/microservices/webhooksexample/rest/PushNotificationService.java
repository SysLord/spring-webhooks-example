package de.syslord.microservices.webhooksexample.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import de.syslord.microservices.webhooksexample.events.Events;
import de.syslord.microservices.webhooksexample.subscription.Subscription;
import de.syslord.microservices.webhooksexample.subscription.SubscriptionException;
import de.syslord.microservices.webhooksexample.subscription.SubscriptionRepository;
import de.syslord.microservices.webhooksexample.subscription.UserSubscriptions;

@RestController
public class PushNotificationService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private Events events;

	// TODO auth
	@Secured({ "ROLE_SUBSCRIBER" })
	@PostMapping(
			path = "/subscription",
			name = "/subscription",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<UserSubscriptions> createSubscribe(
			@RequestBody Subscription subscription,
			Principal principal) {

		// SimpleSecurityExpressionRoot security = SimpleSecurityExpressionRoot.getFromPrincipal(principal);
		// security.hasEvent(subscription.getEvent())

		String username = principal.getName();
		try {
			subscriptionRepository.add(username, subscription);
		} catch (SubscriptionException ex) {
			// TODO C.Helmer 10.06.2017
		}

		return ResponseEntity.ok().body(subscriptionRepository.getSubscriptionsForUser(username));
	}

	@Secured({ "ROLE_SUBSCRIBER" })
	@DeleteMapping(
			path = "/subscription/{id}",
			name = "/subscription/{id}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<UserSubscriptions> deleteSubscription(
			@PathVariable String id,
			Principal principal) {

		String username = principal.getName();
		UserSubscriptions userSubscriptions = subscriptionRepository.getSubscriptionsForUser(username);

		userSubscriptions.delete(id);

		return ResponseEntity.ok().body(subscriptionRepository.getSubscriptionsForUser(username));
	}

	@Secured({ "ROLE_SUBSCRIBER" })
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
