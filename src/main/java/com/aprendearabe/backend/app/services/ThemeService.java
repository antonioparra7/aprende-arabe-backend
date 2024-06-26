package com.aprendearabe.backend.app.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Theme;
import com.aprendearabe.backend.app.models.repositories.IThemeRepository;

@Service
public class ThemeService implements ICrudService<Theme> {
	@Autowired
	private IThemeRepository themeRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Theme> getAll() {
		return themeRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Theme> getAllByLevelId(Long id){
		return themeRepository.findAllByLevelId(id);
	}
	
	@Transactional(readOnly = true)
	public Theme getByName(String name){
		return themeRepository.findByName(name).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Theme getById(Long id) {
		return themeRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Theme save(Theme t) {
		return themeRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		themeRepository.deleteById(id);
	}
}
