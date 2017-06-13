package de.syslord.microservices.webhooks.subscription;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import de.syslord.microservices.webhooks.events.SubscriptionEvent;
import de.syslord.microservices.webhooks.utils.AsyncServiceCaller;
import de.syslord.microservices.webhooks.utils.ServiceCall;

public abstract class SubscriptionRepository {

	private static final Logger logger = LoggerFactory.getLogger(SubscriptionRepository.class);

	@Autowired
	private AsyncServiceCaller asyncServiceCaller;

	@PreAuthorize("hasRole('ROLE_SUBSCRIBER') && @eventSecurity.hasEventPermission(#subscription.event)")
	public abstract void add(String username, Subscription subscription) throws SubscriptionException;

	@PreAuthorize("hasRole('ROLE_SUBSCRIBER') && @eventSecurity.hasEventPermission(#subscription.event)")
	public abstract void patch(String username, Subscription subscription) throws SubscriptionException;

	@PreAuthorize("hasRole('ROLE_SUBSCRIBER')")
	public abstract void delete(String username, String id);

	@PreAuthorize("hasRole('ROLE_SUBSCRIBER')")
	public abstract UserSubscriptions getSubscriptionsForUser(String username);

	protected abstract List<Subscription> getSubscriptionsForEvent(String eventname);

	public void fireEvent(SubscriptionEvent subscriptionEvent) {
		List<Subscription> callList = getSubscriptionsForEvent(subscriptionEvent.getName());

		String owners = callList.stream()
			.map(s -> s.getOwner())
			.distinct()
			.collect(Collectors.joining(", "));

		logger.info("fire event {}: {} calls for {}", subscriptionEvent.getName(), callList.size(), owners);

		callList.stream()
			.map(subscription -> {
				String address = subscriptionEvent.applyPlaceholder(subscription.getRemoteAdress());
				String body = subscriptionEvent.applyPlaceholder(subscription.getPostBody());

				ServiceCall serviceCall = new ServiceCall(
						subscription.getHttpMethod(), address,
						subscription.getUsername(), subscription.getPassword(),
						body);
				return serviceCall;
			})
			.forEach(serviceCall -> asyncServiceCaller.enqueue(serviceCall));
	}

}
