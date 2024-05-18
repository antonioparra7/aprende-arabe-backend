package com.aprendearabe.backend.app.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aprendearabe.backend.app.models.entities.Tutorial;

public interface ITutorialRepository extends JpaRepository<Tutorial, Long> {
	@Query("SELECT t FROM Tutorial t WHERE t.link=?1")
	Optional<Tutorial> findByLink(String link);
}
