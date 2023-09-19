package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Lesson;

public interface ILessonRepository extends JpaRepository<Lesson, Long> {

}
