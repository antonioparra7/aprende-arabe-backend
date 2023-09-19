package com.aprendearabe.backend.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Level;
import com.aprendearabe.backend.app.models.repositories.ILevelRepository;

@Service
public class LevelService implements ICrudService<Level> {
	@Autowired
	private ILevelRepository levelRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Level> getAll() {
		return levelRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Integer getCountAll() {
		return levelRepository.findAll().size();
	}

	@Override
	@Transactional(readOnly = true)
	public Level getById(Long id) {
		return levelRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Level save(Level t) {
		return levelRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		levelRepository.deleteById(id);
	}
}
