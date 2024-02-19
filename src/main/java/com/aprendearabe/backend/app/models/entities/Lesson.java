package com.aprendearabe.backend.app.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "lessons")
public class Lesson implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "theme_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Theme theme;
	@JsonIgnore
	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
	private List<Content> contents;
	@JsonIgnore
	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
	private List<Test> tests;
	@JsonIgnore
	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
	private List<Rating> ratings;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	public Lesson() {
		this.contents = new ArrayList<>();
		this.tests = new ArrayList<>();
		this.ratings = new ArrayList<>();
	}

	public Lesson(String name, Theme theme) {
		this.name = name;
		this.theme = theme;
		this.contents = new ArrayList<>();
		this.tests = new ArrayList<>();
		this.ratings = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}
	
	public void addContent(Content content) {
		this.contents.add(content);
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}
	
	public void addTest(Test test) {
		this.tests.add(test);
	}
	
	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public void addRating(Rating rating) {
		this.ratings.add(rating);
	}

	private static final long serialVersionUID = 1L;
}
