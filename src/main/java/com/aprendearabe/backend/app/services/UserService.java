package com.aprendearabe.backend.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.User;
import com.aprendearabe.backend.app.models.repositories.IUserRepository;

@Service
public class UserService implements ICrudService<User> {
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User getById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public User getByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public User getByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	@Transactional
	public User save(User t) {
		return userRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
}