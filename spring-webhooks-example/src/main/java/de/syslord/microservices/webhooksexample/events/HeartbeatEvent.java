package de.syslord.microservices.webhooksexample.events;

import java.time.LocalDateTime;

import de.syslord.microservices.webhooks.events.SubscriptionEvent;

public class HeartbeatEvent extends SubscriptionEvent {

	public static final String EVENT_NAME = "HEARTBEAT";

	private HeartbeatEvent() {
	}

	public static HeartbeatEvent create(LocalDateTime time) {
		HeartbeatEvent heartbeat = new HeartbeatEvent();
		heartbeat.addPlaceholder("{time}", time.toString());
		return heartbeat;
	}

	@Override
	public String getName() {
		return EVENT_NAME;
	}

}
