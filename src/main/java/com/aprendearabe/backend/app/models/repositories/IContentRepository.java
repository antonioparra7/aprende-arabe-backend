package com.aprendearabe.backend.app.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aprendearabe.backend.app.models.entities.Content;

public interface IContentRepository extends JpaRepository<Content, Long>{
	@Query("SELECT t FROM Content t WHERE t.lesson.id=?1")
	List<Content> findAllByLessonId(Long id);
}
