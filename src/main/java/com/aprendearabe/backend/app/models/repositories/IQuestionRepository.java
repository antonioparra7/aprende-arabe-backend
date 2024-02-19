package com.aprendearabe.backend.app.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aprendearabe.backend.app.models.entities.Question;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
	@Query("SELECT t FROM Question t WHERE t.test.id=?1")
	List<Question> findAllByTestId(Long id);
}
