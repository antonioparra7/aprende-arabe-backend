package com.aprendearabe.backend.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Question;
import com.aprendearabe.backend.app.models.repositories.IQuestionRepository;

@Service
public class QuestionService implements ICrudService<Question> {
	@Autowired
	private IQuestionRepository questionRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Question> getAll() {
		return questionRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Question> getAllByTestId(Long id) {
		return questionRepository.findAllByTestId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Question getById(Long id) {
		return questionRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Question save(Question t) {
		return questionRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		questionRepository.deleteById(id);
	}
}