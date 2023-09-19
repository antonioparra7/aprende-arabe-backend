package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Country;

public interface ICountryRepository extends JpaRepository<Country, Long> {
	
}
