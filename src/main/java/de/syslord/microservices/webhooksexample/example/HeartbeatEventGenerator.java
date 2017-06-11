package de.syslord.microservices.webhooksexample.example;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.syslord.microservices.webhooksexample.subscription.SubscriptionRepository;

@Component
public class HeartbeatEventGenerator {

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Scheduled(fixedDelay = 5000)
	public void fireEvent() {
		HeartbeatEvent heartbeat = HeartbeatEvent.create(LocalDateTime.now());
		subscriptionRepo.fireEvent(heartbeat);
	}

}
