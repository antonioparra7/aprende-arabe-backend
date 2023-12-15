package com.aprendearabe.backend.app.models.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "parameters")
public class Parameter implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String name;
	@Column(nullable = true)
	private String provider;
	@Column(nullable = true)
	private String endpoint;
	@Column(nullable = true)
	private String host;
	@Column(nullable = true)
	private String keyEndpoint;
	@Column(nullable = true)
	private String location;
	@Column(columnDefinition = "MEDIUMBLOB", nullable = true)
	@Lob
	private byte[] image;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	public Parameter() {
	}

	public Parameter(String name, String provider, String endpoint, String host, String keyEndpoint, String location,
			byte[] image) {
		super();
		this.name = name;
		this.provider = provider;
		this.endpoint = endpoint;
		this.host = host;
		this.keyEndpoint = keyEndpoint;
		this.location = location;
		this.image = image;
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

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getKeyEndpoint() {
		return keyEndpoint;
	}

	public void setKeyEndpoint(String keyEndpoint) {
		this.keyEndpoint = keyEndpoint;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	private static final long serialVersionUID = 1L;
}
