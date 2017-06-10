package de.syslord.microservices.webhooksexample.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableScheduling
@EnableAsync
@EnableAutoConfiguration
@ComponentScan("de.syslord.microservices.webhooksexample")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}