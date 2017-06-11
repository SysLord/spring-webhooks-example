package de.syslord.microservices.webhooksexample.events;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

public class Events {

	public static final Map<String, Supplier<SubscriptionEvent>> eventsAndExamples = Maps.newHashMap();

	static {
		eventsAndExamples.put(Heartbeat.EVENT_NAME, () -> Heartbeat.create(LocalDateTime.now()));
	}

	public static Map<String, SubscriptionEvent> getExamples() {
		Map<String, SubscriptionEvent> examples = Events.eventsAndExamples.entrySet().stream()
			.collect(
					Collectors.toMap(
							e -> e.getKey(), e -> e.getValue().get()));

		return examples;
	}

}
