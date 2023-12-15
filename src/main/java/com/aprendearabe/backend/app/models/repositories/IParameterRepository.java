package com.aprendearabe.backend.app.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Parameter;

public interface IParameterRepository extends JpaRepository<Parameter, Long> {
	Optional<Parameter> findByName(String name);
}
