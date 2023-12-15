package com.aprendearabe.backend.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Lesson;
import com.aprendearabe.backend.app.models.repositories.ILessonRepository;

@Service
public class LessonService implements ICrudService<Lesson> {
	@Autowired
	private ILessonRepository lessonRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Lesson> getAll() {
		return lessonRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Lesson> getAllByThemeId(Long id){
		return lessonRepository.findAllByThemeId(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Lesson getById(Long id) {
		return lessonRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Lesson save(Lesson t) {
		return lessonRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		lessonRepository.deleteById(id);
	}
}
