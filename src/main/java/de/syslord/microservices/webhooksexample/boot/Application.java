package de.syslord.microservices.webhooksexample.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;

@SpringBootApplication
@EnableWebMvc
@EnableScheduling
@EnableAsync
@EnableAutoConfiguration
@ComponentScan("de.syslord.microservices.webhooksexample")

// for Hystrix annotations
@EnableAspectJAutoProxy
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// probably best practice: Spring managed RestTemplate to support Sleuth etc.
	@Bean
	public static RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public static HystrixCommandAspect hystrixAspect() {
		return new HystrixCommandAspect();
	}

}