package com.aprendearabe.backend.app.models.entities;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String question;
	private String response_a;
	private String response_b;
	private String response_c;
	private String response_d;
	private String response_correct;
	private String image;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "test_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Test test;

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	public Question() {
	}

	public Question(String question, String response_a, String response_b, String response_c, String response_d,
			String response_correct, String image, Test test) {
		this.question = question;
		this.response_a = response_a;
		this.response_b = response_b;
		this.response_c = response_c;
		this.response_d = response_d;
		this.response_correct = response_correct;
		this.image = image;
		this.test = test;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getResponse_a() {
		return response_a;
	}

	public void setResponse_a(String response_a) {
		this.response_a = response_a;
	}

	public String getResponse_b() {
		return response_b;
	}

	public void setResponse_b(String response_b) {
		this.response_b = response_b;
	}

	public String getResponse_c() {
		return response_c;
	}

	public void setResponse_c(String response_c) {
		this.response_c = response_c;
	}

	public String getResponse_d() {
		return response_d;
	}

	public void setResponse_d(String response_d) {
		this.response_d = response_d;
	}

	public String getResponse_correct() {
		return response_correct;
	}

	public void setResponse_correct(String response_correct) {
		this.response_correct = response_correct;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	private static final long serialVersionUID = 1L;
}
