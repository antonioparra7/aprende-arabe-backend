package com.aprendearabe.backend.app.models.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tutorials")
public class Tutorial implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String link;
	private String destinedTo;
	
	public Tutorial() {
	}

	public Tutorial(String name, String description, String link, String destinedTo) {
		this.name = name;
		this.description = description;
		this.link = link;
		this.destinedTo = destinedTo;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDestinedTo() {
		return destinedTo;
	}

	public void setDestinedTo(String destinedTo) {
		this.destinedTo = destinedTo;
	}
	
	private static final long serialVersionUID = 1L;

}
