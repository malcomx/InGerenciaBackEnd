package com.ingerencia.test.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ingerencia.test.api.entity.HackerNewsEntity;

@Repository
public interface HackerNewsRepository extends CrudRepository<HackerNewsEntity, String> {

	/**
	 * Actualiza la noticia
	 * @param id - Identificador de la noticia
	 * @param delete - Valor booleano, indicando si se elimina o no
	 */
	@Modifying
	@Query("update HackerNews n set n.deleted = ?1 where n.objectID = ?2")
	void setFixedDeletedFor(Boolean delete, String id);
	
	/**
	 * Consulta las todas las noticias que aun no han sido eliminadas
	 * @return Lista de noticias no eliminadas
	 */
	List<HackerNewsEntity> findByDeletedFalseOrderByCreatedDesc();
}
