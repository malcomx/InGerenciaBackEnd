package com.ingerencia.test.api.manager;

import java.util.List;

import com.ingerencia.test.api.exception.InGerenciaException;
import com.ingerencia.test.api.model.HackerNewsModel;
import com.ingerencia.test.api.response.HackerNewsResponse;

public interface ClientHackerNewsManager {

	/**
	 * 
	 * @return
	 * @throws InGerenciaException
	 */
	List<HackerNewsModel> getAllNews() throws InGerenciaException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws InGerenciaException
	 */
	List<HackerNewsModel> removeNews(String id) throws InGerenciaException;
	
	/**
	 * 
	 * @param remotes
	 * @throws InGerenciaException
	 */
	void saveRemoteHackerNews(HackerNewsResponse remote) throws InGerenciaException;
}
