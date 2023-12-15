package com.aprendearabe.backend.app.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Rating;
import com.aprendearabe.backend.app.models.repositories.IRatingRepository;

@Service
public class RatingService implements ICrudService<Rating> {
	@Autowired
	private IRatingRepository ratingRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Rating> getAll() {
		return ratingRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Rating> getAllByLessonId(Long id){
		return ratingRepository.findAllByLessonId(id);
	}
	
	@Transactional(readOnly = true)
	public List<Rating> getAllByUserId(Long id){
		return ratingRepository.findAllByUserId(id);
	}
	
	@Transactional(readOnly = true)
	public Rating getByLessonIdAndUserId(Long lessonId, Long userId){
		return ratingRepository.findByLessonIdAndUserId(lessonId, userId).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Rating getById(Long id) {
		return ratingRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Rating save(Rating t) {
		return ratingRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		ratingRepository.deleteById(id);
	}
}