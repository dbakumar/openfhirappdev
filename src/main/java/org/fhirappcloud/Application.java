package org.fhirappcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication (scanBasePackages = "org.fhirappcloud")
public class Application extends WebMvcConfigurerAdapter{
 	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
