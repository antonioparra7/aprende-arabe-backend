package com.aprendearabe.backend.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Tutorial;
import com.aprendearabe.backend.app.models.repositories.ITutorialRepository;

@Service
public class TutorialService implements ICrudService<Tutorial> {
	@Autowired
	private ITutorialRepository tutorialRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Tutorial> getAll() {
		return tutorialRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Tutorial getById(Long id) {
		return tutorialRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public Tutorial getByLink(String link) {
		return tutorialRepository.findByLink(link).orElse(null);
	}
	

	@Override
	@Transactional
	public Tutorial save(Tutorial t) {
		return tutorialRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		tutorialRepository.deleteById(id);
	}
}