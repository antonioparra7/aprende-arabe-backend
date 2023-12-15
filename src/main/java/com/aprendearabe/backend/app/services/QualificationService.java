package com.aprendearabe.backend.app.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Qualification;
import com.aprendearabe.backend.app.models.repositories.IQualificationRepository;

@Service
public class QualificationService implements ICrudService<Qualification> {
	@Autowired
	private IQualificationRepository qualificationRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Qualification> getAll() {
		return qualificationRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Qualification> getAllByTestId(Long id){
		return qualificationRepository.findAllByTestId(id);
	}
	
	@Transactional(readOnly = true)
	public List<Qualification> getAllByUserId(Long id){
		return qualificationRepository.findAllByUserId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Qualification getById(Long id) {
		return qualificationRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Qualification save(Qualification t) {
		return qualificationRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		qualificationRepository.deleteById(id);
	}
}