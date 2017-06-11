package de.syslord.microservices.webhooksexample.events;

import java.time.LocalDateTime;

public class Heartbeat extends SubscriptionEvent {

	public static final String EVENT_NAME = "HEARTBEAT";

	private Heartbeat() {
	}

	public static Heartbeat create(LocalDateTime time) {
		Heartbeat heartbeat = new Heartbeat();
		heartbeat.addPlaceholder("{time}", time.toString());
		return heartbeat;
	}

	@Override
	public String getName() {
		return EVENT_NAME;
	}

}
