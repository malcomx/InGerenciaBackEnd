package com.ingerencia.test.api.util;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ingerencia.test.api.entity.HackerNewsEntity;
import com.ingerencia.test.api.model.HackerNewsModel;
import com.ingerencia.test.api.response.HackerNewsResponse;

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

	/**
	 * Metodo Utilitario que se encarga de onbtener el titulo de la noticia. Se encarga de obrener el valor desde los 
	 * campos <b>title</b> o <b>story_title</b>
	 * @param hit - Objeto con los datos de la Noticia
	 * @return El titulo de la noticia
	 */
	public String getTitle(HackerNewsResponse.Hit hit) {
		String title = null;
		
		if(StringUtils.isNotBlank(hit.getTitle())){
			 title = hit.getTitle();
		} else if(StringUtils.isNotBlank(hit.getStoryTitle())) {
			title = hit.getStoryTitle();
		} else { 
			title = StringUtils.EMPTY; 
		}
		
		return title;
	}
}
