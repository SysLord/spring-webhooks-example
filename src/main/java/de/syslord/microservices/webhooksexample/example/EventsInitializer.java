package de.syslord.microservices.webhooksexample.example;

import java.time.LocalDateTime;

import de.syslord.microservices.webhooksexample.events.Events;

public class EventsInitializer {

	static {
		Events.addEventType(HeartbeatEvent.EVENT_NAME, () -> HeartbeatEvent.create(LocalDateTime.now()));
		Events.addEventType("LOLCATS", () -> HeartbeatEvent.create(LocalDateTime.now()));
	}

}
