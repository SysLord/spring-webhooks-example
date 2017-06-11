package de.syslord.microservices.webhooksexample.utils;

import java.net.URI;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AsyncServiceCaller {

	LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

	@PostConstruct
	public void init() {
		// TODO
		// start executor:
		// String take = queue.take();
	}

	@PreDestroy
	public void teardown() {
		// TODO
		// stop executor
	}

	@Async
	public void enqueue(String callAddress) {
		// TODO
		// boolean offerSuccessful = queue.offer(callAddress);

		URI uri = UriComponentsBuilder.fromHttpUrl(callAddress).build().toUri();

		System.out.println(uri.toString());

		String result = new RestTemplate().getForObject(uri, String.class);

		// TODO use hystrix, auth, post, vs get, body vs params .... log user
	}

	public void q(String callAddress) {

	}

}
