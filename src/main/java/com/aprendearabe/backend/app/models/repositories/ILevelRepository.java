package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Level;

public interface ILevelRepository extends JpaRepository<Level, Long> {

}
