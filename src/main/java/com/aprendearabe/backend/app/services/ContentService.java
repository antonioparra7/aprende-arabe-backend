package com.aprendearabe.backend.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Content;
import com.aprendearabe.backend.app.models.repositories.IContentRepository;

@Service
public class ContentService implements ICrudService<Content> {
	@Autowired
	private IContentRepository contentRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Content> getAll() {
		return contentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Content getById(Long id) {
		return contentRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Content save(Content t) {
		return contentRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		contentRepository.deleteById(id);
	}
}
