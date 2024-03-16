package com.aprendearabe.backend.app.models.entities;


import java.io.Serializable;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "levels")
public class Level implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String name;
	@Column(columnDefinition = "MEDIUMBLOB")
	@Lob
    private byte[] image;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	@JsonIgnore
	@OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
	private List<Theme> themes;
	@JsonIgnore
	@OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
	private List<Test> tests;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	public Level() {
		this.themes = new ArrayList<>();
		this.tests = new ArrayList<>();
	}

	public Level(String name, byte[] image) {
		this.name = name;
		this.image = image;
		this.themes = new ArrayList<>();
		this.tests = new ArrayList<>();
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}
	
	public void addTheme(Theme theme) {
		this.themes.add(theme);
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
	private static final long serialVersionUID = 1L;
}
