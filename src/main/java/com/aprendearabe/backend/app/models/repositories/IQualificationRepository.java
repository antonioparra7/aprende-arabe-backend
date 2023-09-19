package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Qualification;

public interface IQualificationRepository extends JpaRepository<Qualification, Long> {

}
