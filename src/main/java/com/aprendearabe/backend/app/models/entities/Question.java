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
@Table(name = "questions")
public class Question implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String question;
	private String responseA;
	private String responseB;
	private String responseC;
	private String responseD;
	private String responseCorrect;
	@Column(columnDefinition = "MEDIUMBLOB")
	@Lob
	private byte[] image;
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

	public Question(String question, String responseA, String responseB, String responseC, String responseD,
			String responseCorrect, byte[] image, Test test) {
		this.question = question;
		this.responseA = responseA;
		this.responseB = responseB;
		this.responseC = responseC;
		this.responseD = responseD;
		this.responseCorrect = responseCorrect;
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

	public String getResponseA() {
		return responseA;
	}

	public void setResponseA(String responseA) {
		this.responseA = responseA;
	}

	public String getResponseB() {
		return responseB;
	}

	public void setResponseB(String responseB) {
		this.responseB = responseB;
	}

	public String getResponseC() {
		return responseC;
	}

	public void setResponseC(String responseC) {
		this.responseC = responseC;
	}

	public String getResponseD() {
		return responseD;
	}

	public void setResponseD(String responseD) {
		this.responseD = responseD;
	}

	public String getResponseCorrect() {
		return responseCorrect;
	}

	public void setResponseCorrect(String responseCorrect) {
		this.responseCorrect = responseCorrect;
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

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	private static final long serialVersionUID = 1L;
}
