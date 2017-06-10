package de.syslord.microservices.webhooksexample.boot;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@RestController
public class SwaggerConfig {

	// Redirect / to swagger-ui
	@ApiIgnore
	@RequestMapping(name = "/", value = "/", method = RequestMethod.GET)
	public void method(HttpServletResponse httpServletResponse) {
		// relative url to work with different base urls
		httpServletResponse.setHeader("Location", "./swagger-ui.html");
		httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}

}
