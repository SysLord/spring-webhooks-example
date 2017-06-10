package de.syslord.microservices.webhooksexample.events;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

public class Events {

	public static final Map<String, Supplier<SubscriptionEvent>> eventsExamples = Maps.newHashMap();

	static {
		eventsExamples.put("Heartbeat", () -> Heartbeat.create(LocalDateTime.now()));
	}

}
