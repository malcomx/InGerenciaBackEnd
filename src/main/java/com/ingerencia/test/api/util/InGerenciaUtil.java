package com.ingerencia.test.api.util;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ingerencia.test.api.entity.HackerNewsEntity;
import com.ingerencia.test.api.model.HackerNewsModel;

@Component
public class InGerenciaUtil {

	private PrettyTime pretty = new PrettyTime();

	/**
	 * Retorna el momento relativo desde que ocurrio
	 * @param input
	 * @return
	 */
	private String getRelativeDate(Date input) {
		return pretty.format(input);
	}
	
	public List<HackerNewsModel> build(List<HackerNewsEntity> entities) {
		
		List<HackerNewsModel> response = Collections.emptyList();
		
		// TODO Mapea la respuesta al objeto HackerNewsModel, que contiene los datos de la API
		if(!CollectionUtils.isEmpty(entities)) {
			// Mapea el objeto entity al modelo
			response = entities
					.stream()
					.map(n -> {
						return HackerNewsModel
								.builder()
								.author(n.getAuthor())
								.title(n.getTitle())
								.created(this.getRelativeDate(n.getCreated()))
								.objectID(n.getObjectID())
								.icon("delete_forever")
								.build();
					})
					.collect(Collectors.toList());
		}
		
		return response;
	}
}
