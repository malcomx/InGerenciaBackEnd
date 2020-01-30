package com.ingerencia.test.api.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("serial")
@Builder(builderClassName = "Builder")
@Getter
@ToString(includeFieldNames = true)
public class HackerNewsModel implements Serializable {

	private String title;
	private String author;
	private String created;
	private String objectID;
	private String icon;
}
