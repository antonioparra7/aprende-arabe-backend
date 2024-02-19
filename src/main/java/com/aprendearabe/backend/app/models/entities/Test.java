package com.aprendearabe.backend.app.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "tests")
public class Test implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(columnDefinition = "MEDIUMBLOB")
	@Lob
	private byte[] image;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "lesson_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Lesson lesson;
	@JsonIgnore
	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
	private List<Question> questions;
	@JsonIgnore
	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
	private List<Qualification> qualifications;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	public Test() {
		this.questions = new ArrayList<>();
		this.qualifications = new ArrayList<>();
	}

	public Test(String name, byte[] image, Lesson lesson) {
		this.name = name;
		this.image = image;
		this.lesson = lesson;
		this.questions = new ArrayList<>();
		this.qualifications = new ArrayList<>();
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

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
	}

	public List<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public void addQualification(Qualification qualification) {
		this.qualifications.add(qualification);
	}

	private static final long serialVersionUID = 1L;
}
