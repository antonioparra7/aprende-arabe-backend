package com.aprendearabe.backend.app.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Test;
import com.aprendearabe.backend.app.models.repositories.ITestRepository;

@Service
public class TestService implements ICrudService<Test> {
	@Autowired
	private ITestRepository testRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Test> getAll() {
		return testRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Test> getAllByLevelId(Long id){
		return testRepository.findAllByLevelId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Test getById(Long id) {
		return testRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Test save(Test t) {
		return testRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		testRepository.deleteById(id);
	}
}