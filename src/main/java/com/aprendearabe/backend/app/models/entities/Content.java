package com.aprendearabe.backend.app.models.entities;

import java.io.Serializable;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "contents")
public class Content implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String word;
	private String wordTranslate;
	@Column(columnDefinition = "MEDIUMBLOB")
	@Lob
	private byte[] image;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "lesson_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Lesson lesson;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	public Content() {
	}	

	public Content(String word, String wordTranslate, byte[] image, Lesson lesson) {
		this.word = word;
		this.wordTranslate = wordTranslate;
		this.image = image;
		this.lesson = lesson;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}


	public String getWordTranslate() {
		return wordTranslate;
	}


	public void setWordTranslate(String wordTranslate) {
		this.wordTranslate = wordTranslate;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	private static final long serialVersionUID = 1L;
}
