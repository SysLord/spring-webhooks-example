package de.syslord.microservices.webhooksexample.rest;

import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import de.syslord.microservices.webhooksexample.events.Events;
import de.syslord.microservices.webhooksexample.events.SubscriptionEvent;
import de.syslord.microservices.webhooksexample.subscription.MapBackedSubscriptionRepository;
import de.syslord.microservices.webhooksexample.subscription.Subscription;
import de.syslord.microservices.webhooksexample.subscription.SubscriptionException;
import de.syslord.microservices.webhooksexample.subscription.UserSubscriptions;

@RestController
public class PushNotificationService {

	@Autowired
	private MapBackedSubscriptionRepository subscriptionRepository;

	@Secured({ "ROLE_SUBSCRIBER" })
	@RequestMapping(
			path = "/subscribe",
			name = "/subscribe",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<UserSubscriptions> subscribe(
			@RequestBody Subscription subscription,
			Principal principal) {

		String name = principal.getName();
		// TODO C.Helmer 10.06.2017
		System.out.println("helo" + name);

		try {
			subscriptionRepository.add(name, subscription);
		} catch (SubscriptionException ex) {
			// TODO C.Helmer 10.06.2017
		}

		return ResponseEntity.ok().body(subscriptionRepository.getSubscriptions(name));
	}

	@GetMapping(
			path = "/events",
			name = "/events",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getPossibleEvents(HttpServletRequest httpServletRequest) {

		// TODO check event auth
		// httpServletRequest.isUserInRole("")

		Map<String, SubscriptionEvent> x = Events.eventsExamples.entrySet().stream().collect(
				Collectors.toMap(
						e -> e.getKey(), e -> e.getValue().get()));

		// TODO keine Ahnung was das gibt
		return ResponseEntity.ok().body(x);
	}

}
