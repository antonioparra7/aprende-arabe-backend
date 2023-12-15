package com.aprendearabe.backend.app.models.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aprendearabe.backend.app.models.entities.Rating;

public interface IRatingRepository extends JpaRepository<Rating, Long> {
	@Query("SELECT r FROM Rating r WHERE r.lesson.id=?1")
	List<Rating> findAllByLessonId(Long id);
	
	@Query("SELECT r FROM Rating r WHERE r.user.id=?1")
	List<Rating> findAllByUserId(Long id);
	
	@Query("SELECT r FROM Rating r WHERE r.lesson.id=?1 and r.user.id=?2")
	Optional<Rating> findByLessonIdAndUserId(Long lessonId, Long userId);
}
