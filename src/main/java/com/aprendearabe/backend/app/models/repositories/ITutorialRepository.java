package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Tutorial;

public interface ITutorialRepository extends JpaRepository<Tutorial, Long> {

}
