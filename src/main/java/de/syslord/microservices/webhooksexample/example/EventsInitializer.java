package de.syslord.microservices.webhooksexample.example;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.syslord.microservices.webhooksexample.events.Events;

@Component
public class EventsInitializer {

	@Autowired
	private Events events;

	@PostConstruct
	public void initEvents() {
		events.addEventType(HeartbeatEvent.EVENT_NAME, () -> HeartbeatEvent.create(LocalDateTime.now()));
		events.addEventType("LOLCATS", () -> HeartbeatEvent.create(LocalDateTime.now()));
	}

}
