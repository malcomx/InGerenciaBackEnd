package com.ingerencia.test.api.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingerencia.test.api.exception.InGerenciaException;
import com.ingerencia.test.api.manager.ClientHackerNewsManager;
import com.ingerencia.test.api.model.HackerNewsModel;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class TestController {

	@Setter
	@Autowired
	private ClientHackerNewsManager clientHackerNewsManager;
	
	@CrossOrigin
	@GetMapping(value = "/")
	public String init() {
		return "Started";
	}

	@CrossOrigin
	@GetMapping(value = "/news")
	public List<HackerNewsModel> getHackerNews() throws InGerenciaException {
		
		log.info("Consultando noticias..");
		
		try {
			// Obtiene las noticias
			return this.clientHackerNewsManager.getAllNews();
		
		} catch (InGerenciaException ex) {
			log.error("Error Obteniendo las noticias", ex);
			throw new InGerenciaException("Error Obteniendo las noticias");
		} catch (Exception ex) {
			log.error("Error Obteniendo las noticias", ex);
			throw new InGerenciaException("Error Obteniendo las noticias");
		} 
	}
	
	@CrossOrigin
	@DeleteMapping(value = "/news/{id}")
	public String removeNews(@PathVariable(name = "id") String id) throws InGerenciaException {
//		"Telf: 0212-9080624"
		try {
			if(!StringUtils.isNumeric(id))
				return "El ID de la noticia no contiene el formato adecuado";
			
			// Obtiene las noticias
			return this.clientHackerNewsManager.removeNews(id);
		
		} catch (InGerenciaException ex) {
			log.error("Error eliminando noticia: ", ex);
			throw new InGerenciaException("Error eliminando noticia");
		} catch (Exception ex) {
			log.error("Error eliminando noticia: ", ex);
			throw new InGerenciaException("Error eliminando noticia");
		} 
	}
}
