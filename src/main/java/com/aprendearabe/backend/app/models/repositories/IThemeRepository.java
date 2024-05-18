package com.aprendearabe.backend.app.models.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aprendearabe.backend.app.models.entities.Theme;

public interface IThemeRepository extends JpaRepository<Theme, Long> {
	@Query("SELECT t FROM Theme t WHERE t.level.id=?1")
	List<Theme> findAllByLevelId(Long id);
	Optional<Theme> findByName(String name);
	
}
