package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Rating;

public interface IRatingRepository extends JpaRepository<Rating, Long> {

}
