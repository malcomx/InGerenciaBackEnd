package com.ingerencia.test.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "HackerNews")
@Table(name = "hacker_news")
@ToString(includeFieldNames = true)
@EqualsAndHashCode(of = { "objectID" })
@NamedQueries(
	@NamedQuery(
		name = HackerNewsEntity.SELECT_ALL,
		query = "SELECT n FROM HackerNews n"
	)	
)
public class HackerNewsEntity implements Serializable {
	
	public final static String SELECT_ALL = "newsGetAll";

	@Id
	@Basic
	@Column(name = "id")
	private String objectID;
	
	@Basic
	@Column(name = "title")
	private String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date created;

	@Basic
	@Column(name = "author")
	private String author;
	
	@Column(name = "is_deleted")
	private Boolean deleted = Boolean.FALSE;
}
