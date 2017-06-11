package de.syslord.microservices.webhooksexample.rest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import de.syslord.microservices.webhooksexample.events.Events;
import de.syslord.microservices.webhooksexample.subscription.MapBackedSubscriptionRepository;
import de.syslord.microservices.webhooksexample.subscription.Subscription;
import de.syslord.microservices.webhooksexample.subscription.SubscriptionException;
import de.syslord.microservices.webhooksexample.subscription.UserSubscriptions;

@RestController
public class PushNotificationService {

	@Autowired
	private MapBackedSubscriptionRepository subscriptionRepository;

	@Secured({ "ROLE_SUBSCRIBER" })
	@PostMapping(
			path = "/subscription",
			name = "/subscription",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<UserSubscriptions> createSubscribe(
			@RequestBody Subscription subscription,
			Principal principal) {

		String username = principal.getName();
		// TODO C.Helmer 10.06.2017
		System.out.println("helo" + username);

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

		try {
			userSubscriptions.delete(id);
		} catch (SubscriptionException ex) {
			// TODO C.Helmer 10.06.2017
		}

		return ResponseEntity.ok().body(subscriptionRepository.getSubscriptionsForUser(username));
	}

	@GetMapping(
			path = "/events",
			name = "/events",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getPossibleEvents(HttpServletRequest httpServletRequest) {

		// TODO check event auth
		// httpServletRequest.isUserInRole("")

		return ResponseEntity.ok().body(Events.getExamples());
	}

}
