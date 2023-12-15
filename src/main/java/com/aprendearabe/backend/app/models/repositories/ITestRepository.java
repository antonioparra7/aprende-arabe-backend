package com.aprendearabe.backend.app.models.repositories;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.aprendearabe.backend.app.models.entities.Test;

public interface ITestRepository extends JpaRepository<Test, Long> {
	@Query("SELECT t FROM Test t WHERE t.lesson.id=?1")
	List<Test> findAllByLessonId(Long id);
}
