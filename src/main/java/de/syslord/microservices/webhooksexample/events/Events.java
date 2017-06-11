package de.syslord.microservices.webhooksexample.events;

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

	public static void addEventType(String name, Supplier<SubscriptionEvent> exampleSupplier) {
		eventsAndExamples.put(name, exampleSupplier);
	}

	@PostFilter("@eventSecurity.hasEventPermission(filterObject.eventname)")
	public List<EventExample> getExamples() {

		List<EventExample> examples = Events.eventsAndExamples.entrySet().stream()
			.map(entry -> new EventExample(entry.getKey(), entry.getValue().get()))
			.collect(Collectors.toList());

		return examples;
	}

}
