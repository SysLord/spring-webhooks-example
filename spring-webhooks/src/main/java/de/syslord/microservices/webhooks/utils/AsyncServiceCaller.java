package de.syslord.microservices.webhooks.utils;

public interface AsyncServiceCaller {

	void enqueue(ServiceCall serviceCall);
}
