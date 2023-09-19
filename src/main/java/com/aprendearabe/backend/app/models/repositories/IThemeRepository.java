package com.aprendearabe.backend.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aprendearabe.backend.app.models.entities.Theme;

public interface IThemeRepository extends JpaRepository<Theme, Long> {

}
