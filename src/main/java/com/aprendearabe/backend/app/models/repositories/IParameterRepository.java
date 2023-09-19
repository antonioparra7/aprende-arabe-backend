package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Parameter;

public interface IParameterRepository extends JpaRepository<Parameter, Long> {

}
