package de.syslord.microservices.webhooks.utils;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import de.syslord.microservices.webhooks.subscription.SubscriptionCallMethod;

@Component
public class DefaultAsyncServiceCaller implements AsyncServiceCaller {

	private static final Logger logger = LoggerFactory.getLogger(DefaultAsyncServiceCaller.class);

	private LinkedBlockingQueue<ServiceCall> queue = new LinkedBlockingQueue<>();

	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Autowired
	private RestTemplate restTemplate;

	@PostConstruct
	private void init() {
		scheduler.schedule(SafeRunnable.of(() -> readQueueForever()), 5, TimeUnit.SECONDS);
	}

	@PreDestroy
	private void teardown() {
		scheduler.shutdownNow();
	}

	private void readQueueForever() {
		while (!Thread.currentThread().isInterrupted()) {

			try {
				ServiceCall serviceCall = queue.take();
				call(serviceCall);

			} catch (InterruptedException e) {
				return;
			}
		}
	}

	@Override
	public void enqueue(ServiceCall serviceCall) {
		boolean added = queue.offer(serviceCall);
		if (!added) {
			logger.warn("queue is full!");
		}
	}

	@HystrixCommand(
			commandKey = "SubscriptionNotification",
			fallbackMethod = "fallback",
			commandProperties = {
					@HystrixProperty(name = HystrixProperties.FALLBACK_ENABLED, value = "true")
			})
	public void call(ServiceCall serviceCall) {
		URI uri = UriComponentsBuilder
			.fromHttpUrl(serviceCall.getAddress())
			.build().toUri();

		HttpEntity<?> httpEntity = serviceCall.buildHttpEntity();

		restTemplate.exchange(
				uri,
				SubscriptionCallMethod.POST.equals(serviceCall.getHttpMethod()) ? HttpMethod.POST : HttpMethod.GET,
				httpEntity,
				Object.class);
	}

	public void fallback(String callAddress) {
		logger.warn("failed to call subscription {}", callAddress);
	}

}
