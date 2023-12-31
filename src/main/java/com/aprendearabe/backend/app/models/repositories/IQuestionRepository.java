package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Question;

public interface IQuestionRepository extends JpaRepository<Question, Long> {

}
