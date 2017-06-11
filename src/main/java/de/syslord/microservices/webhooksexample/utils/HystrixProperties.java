package de.syslord.microservices.webhooksexample.utils;

public interface HystrixProperties {

	String FALLBACK_ENABLED = "fallback.enabled";

	String TIMEOUT_ENABLED = "execution.timeout.enabled";

	String TIMEOUT_MS = "execution.isolation.thread.timeoutInMilliseconds";

}
