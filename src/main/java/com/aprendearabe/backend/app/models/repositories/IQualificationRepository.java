package com.aprendearabe.backend.app.models.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aprendearabe.backend.app.models.entities.Qualification;

public interface IQualificationRepository extends JpaRepository<Qualification, Long> {
	@Query("SELECT q FROM Qualification q WHERE q.test.id=?1")
	List<Qualification> findAllByTestId(Long id);
	
	@Query("SELECT q FROM Qualification q WHERE q.user.id=?1")
	List<Qualification> findAllByUserId(Long id);
	
	@Query("SELECT q FROM Qualification q WHERE q.test.id=?1 and q.user.id=?2")
	Optional<Qualification> findByTestIdAndUserId(Long testId, Long userId);
}
