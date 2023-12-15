package com.aprendearabe.backend.app.models.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aprendearabe.backend.app.models.entities.Lesson;

public interface ILessonRepository extends JpaRepository<Lesson, Long> {
	@Query("SELECT l FROM Lesson l WHERE l.theme.id=?1")
	List<Lesson> findAllByThemeId(Long id);
}
