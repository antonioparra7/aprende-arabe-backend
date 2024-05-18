package com.aprendearabe.backend.app.models.entities;

import java.util.Collection;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	@Size(min = 3,max=25)
	private String firstName;
	@NotEmpty
	@Size(min = 3,max=35)
	private String lastName;
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	private Integer age;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@NotEmpty
	@Email
	@Column(unique = true)
	private String email;
	@NotEmpty
	@Size(min = 9,max=9)
	@Pattern(regexp = "^[0-9]*$")
	@Column(unique = true)
	private String phone;
	@NotEmpty
	@Size(min=4, max=15)
	@Column(unique = true)
	private String username;
	@NotEmpty
	@JsonIgnore
	private String password;
	@Column(columnDefinition = "MEDIUMBLOB")
	@Lob
    private byte[] image;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	private boolean verified;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Column(nullable = true)
	private Integer maxNumberStudents;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Country country;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id",nullable = true)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Level level;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Rating> ratings;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Qualification> qualifications;

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void addRating(Rating rating) {
		this.ratings.add(rating);
	}

	public void addQualification(Qualification qualification) {
		this.qualifications.add(qualification);
	}

	private static final long serialVersionUID = 1L;
}
