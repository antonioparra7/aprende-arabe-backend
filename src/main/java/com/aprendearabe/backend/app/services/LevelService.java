package com.aprendearabe.backend.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Level;
import com.aprendearabe.backend.app.models.entities.User;
import com.aprendearabe.backend.app.models.repositories.ILevelRepository;
import com.aprendearabe.backend.app.models.repositories.IUserRepository;

@Service
public class LevelService implements ICrudService<Level> {
	@Autowired
	private ILevelRepository levelRepository;
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Level> getAll() {
		return levelRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Level getById(Long id) {
		return levelRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public Level getByUserId(Long id) {
		User user = userRepository.findById(id).orElse(null);
		if (user != null) {
			if (user.getLevel() != null) {
				return levelRepository.findById(user.getLevel().getId()).orElse(null);
			}
			return null;
		}
		return null;
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
