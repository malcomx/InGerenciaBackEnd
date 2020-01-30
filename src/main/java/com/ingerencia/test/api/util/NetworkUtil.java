package com.ingerencia.test.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ingerencia.test.api.response.HackerNewsResponse;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NetworkUtil {

	@Setter
	@Autowired
	private WebClient.Builder webClient;
	
	@Value("${hackernews.url.endpoint}")
	private String hackerNewsEndpoint;
	
	/**
	 * Obtiene las noticias desde la API 
	 * @return el modelo con los datso de la noticia
	 */
	public HackerNewsResponse hackerNewsGetWithBlock() {
		try {
			// TODO Obtiene las noticias desde la API de Hacker News
			HackerNewsResponse remoteResponse = this.webClient.build()
				.get()
				.uri(hackerNewsEndpoint)
				.retrieve()
				.bodyToMono(HackerNewsResponse.class)
				.block();
			
			return remoteResponse;
			
		} catch (Exception ex) {
			log.error("Error conectandose a {}", this.hackerNewsEndpoint, ex);
		}
		
		return null;
	}
}
