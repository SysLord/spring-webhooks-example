package de.syslord.microservices.webhooksexample.events;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.syslord.microservices.webhooksexample.subscription.MapBackedSubscriptionRepository;

@Component
public class EventGenerator {

	// TODO use interface
	@Autowired
	private MapBackedSubscriptionRepository subscriptionRepo;

	@Scheduled(fixedDelay = 5000)
	public void fireEvent() {
		subscriptionRepo.fireEvent(Heartbeat.create(LocalDateTime.now()));
	}

}
