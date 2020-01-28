package com.ingerencia.test.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class IngerenciaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngerenciaTestApplication.class, args);
	}

	@Bean
	public WebClient.Builder webClient() {
		return WebClient.builder(); 
	}
}
