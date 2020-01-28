package com.ingerencia.test.api.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HackerNewsResponse implements Serializable {

	private List<Hit> hits;
	private Long nbHits;
	private Long page;
	private Long nbPages;
	private Long hitsPerPage;
	private Boolean exhaustiveNbHits;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Hit {
		@JsonProperty(value = "created_at")
		private Date created;
		private String title;
		private String author;
		@JsonProperty(value = "story_title")
		private String storyTitle;
		private String url;
		private String objectID;
		
	}
}
