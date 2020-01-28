package com.ingerencia.test.api.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HackerNewsModel implements Serializable {

	private String title;
	private String author;
	private Date created;
	private String objectID;
}
