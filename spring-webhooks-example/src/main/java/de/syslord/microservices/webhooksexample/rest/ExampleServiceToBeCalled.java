package de.syslord.microservices.webhooksexample.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleServiceToBeCalled {

	public static final String STATS_ENDPOINT = "/stats";

	public static final String CALLME1_ENDPOINT = "/callme1";

	private List<String> callingList = Collections.synchronizedList(new ArrayList<>());

	@RequestMapping(
			path = CALLME1_ENDPOINT,
			name = CALLME1_ENDPOINT,
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> called(
			@RequestParam("param") String param) {

		String message = String.format("%s %s", LocalDateTime.now(), param);
		callingList.add(message);

		System.out.println(message);

		return ResponseEntity.ok("OK");
	}

	@RequestMapping(
			path = STATS_ENDPOINT,
			name = STATS_ENDPOINT,
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> stats() {
		return ResponseEntity.ok(callingList.stream().collect(Collectors.joining("\n")));
	}

}
