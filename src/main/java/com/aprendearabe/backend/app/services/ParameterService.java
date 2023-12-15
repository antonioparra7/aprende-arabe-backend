package com.aprendearabe.backend.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Parameter;
import com.aprendearabe.backend.app.models.repositories.IParameterRepository;

@Service
public class ParameterService implements ICrudService<Parameter> {
	@Autowired
	private IParameterRepository parameterRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Parameter> getAll() {
		return parameterRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Parameter getById(Long id) {
		return parameterRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Parameter getByName(String name) {
		return parameterRepository.findByName(name).orElse(null);
	}

	@Override
	@Transactional
	public Parameter save(Parameter t) {
		return parameterRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		parameterRepository.deleteById(id);
	}
}