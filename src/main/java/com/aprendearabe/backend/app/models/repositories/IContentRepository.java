package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aprendearabe.backend.app.models.entities.Content;

public interface IContentRepository extends JpaRepository<Content, Long>{

}
