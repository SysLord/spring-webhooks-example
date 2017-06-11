package de.syslord.microservices.webhooksexample.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
public class Events {

	private static final Map<String, Supplier<SubscriptionEvent>> eventsAndExamples = Maps.newHashMap();

	static {
		eventsAndExamples.put(Heartbeat.EVENT_NAME, () -> Heartbeat.create(LocalDateTime.now()));
		// TODO tests
		eventsAndExamples.put("LOLCATS", () -> Heartbeat.create(LocalDateTime.now()));
	}

	@PostFilter("@eventSecurity.hasEventPermission(filterObject.eventname)")
	public List<ExampleObject> getExamples() {

		List<ExampleObject> examples = Events.eventsAndExamples.entrySet().stream()
			.map(entry -> new ExampleObject(entry.getKey(), entry.getValue().get()))
			.collect(Collectors.toList());

		return examples;
	}

}
