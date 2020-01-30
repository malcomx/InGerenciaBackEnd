package com.ingerencia.test.api.manager.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ingerencia.test.api.entity.HackerNewsEntity;
import com.ingerencia.test.api.exception.InGerenciaException;
import com.ingerencia.test.api.manager.ClientHackerNewsManager;
import com.ingerencia.test.api.model.HackerNewsModel;
import com.ingerencia.test.api.repository.HackerNewsRepository;
import com.ingerencia.test.api.response.HackerNewsResponse;
import com.ingerencia.test.api.util.InGerenciaUtil;
import com.ingerencia.test.api.util.NetworkUtil;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultClientHackerNewsManager implements ClientHackerNewsManager {

	@Setter
	@Autowired
	private HackerNewsRepository newsRepository;

	@Setter
	@Autowired
	private NetworkUtil networkUtil;

	@Setter
	@Autowired
	private InGerenciaUtil inGerenciaUtil;
	
	@Transactional
	@Override
	public List<HackerNewsModel> getAllNews() throws InGerenciaException {
		
		try {
			// TODO Obtiene las noticias desde la API de Hacker News
			HackerNewsResponse remoteResponse = this.networkUtil.hackerNewsGetWithBlock();
			
			// TODO Guarda las noticias
			this.saveRemoteHackerNews(remoteResponse);
			
			// TODO Consulta en la base de datos las noticias activas
			List<HackerNewsEntity> entities = (List<HackerNewsEntity>) this.newsRepository.findByDeletedFalseOrderByCreatedDesc();
			
			return inGerenciaUtil.build(entities);
			
		} catch (Exception ex) {
			log.error("Error obteniendo las noticias", ex);
			throw new InGerenciaException("Error obteniendo las noticias", ex);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public List<HackerNewsModel> removeNews(String id) throws InGerenciaException {
		try {
			// TODO Coloca con estado ELIMINADO = TRUE al registro de noticia
			this.newsRepository.setFixedDeletedFor(Boolean.TRUE, id);

			// TODO Consulta en la base de datos las noticias activas
			List<HackerNewsEntity> entities = (List<HackerNewsEntity>) this.newsRepository.findByDeletedFalseOrderByCreatedDesc();
			
			return inGerenciaUtil.build(entities);
//			return this.getAllNews();
			
		} catch (Exception ex) {
			log.error("Error persistiendo en Remove News", ex);
			throw new InGerenciaException("Error eliminando noticia");
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public void saveRemoteHackerNews(HackerNewsResponse remote) throws InGerenciaException {
		try {
			if(null != remote && !CollectionUtils.isEmpty(remote.getHits())) {
				// TODO Obtiene las noticias eliminadas
				List<HackerNewsEntity> allNews = (List<HackerNewsEntity>) this.newsRepository.findAll();
				
				List<HackerNewsEntity> response = Collections.emptyList();
				
				if(!CollectionUtils.isEmpty(allNews)) {
					// Obtiene las entidades que no han sido eliminadas
					response = remote.getHits()
							.stream()
							// Filtro. Solo permite mostrar las noticias que no hayan sido eliminadas
							.filter(p -> StringUtils.isNotBlank(p.getObjectID()) 
									&& !allNews.stream().anyMatch(v -> !v.getDeleted() && v.getObjectID().equals(p.getObjectID())))
							.map(n -> {
								return new HackerNewsEntity(
										n.getObjectID(), 
										getTitle(n), 
										n.getCreated(), 
										n.getAuthor(), 
										Boolean.FALSE);
							})
							.collect(Collectors.toList());
					
					// TODO Verifica los repetidos
					response = response.stream()
						.filter(r -> allNews.stream().anyMatch(rr -> rr.getObjectID().equals(r.getObjectID())))
						.collect(Collectors.toList());

				} else {
					response = remote.getHits()
							.stream()
							.map(n -> {
								return new HackerNewsEntity(
										n.getObjectID(), 
										getTitle(n), 
										n.getCreated(), 
										n.getAuthor(), 
										Boolean.FALSE);
							})
							.collect(Collectors.toList());
				}
				
				if(!CollectionUtils.isEmpty(response)) {
					// Guarda el lote de noticias
					this.newsRepository.saveAll(response);
				}
			}
		} catch (Exception ex) {
			log.error("Error persistiendo  Noticias ", ex);
			throw new InGerenciaException("Error persistiendo  Noticias");
		}
	}
	
	/**
	 * Metodo Utilitario que se encarga de onbtener el titulo de la noticia. Se encarga de obrener el valor desde los 
	 * campos <b>title</b> o <b>story_title</b>
	 * @param hit - Objeto con los datos de la Noticia
	 * @return El titulo de la noticia
	 */
	private String getTitle(HackerNewsResponse.Hit hit) {
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
