package de.syslord.microservices.webhooksexample.utils;

import java.net.URI;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AsyncServiceCaller {

	@Async
	public void enqueue(String callAddress) {

		URI uri = UriComponentsBuilder.fromHttpUrl(callAddress).build().toUri();

		System.out.println(uri.toString());

		String result = new RestTemplate().getForObject(uri, String.class);

		// TODO use hystrix, auth, post, vs get, body vs params .... log user
	}

}
