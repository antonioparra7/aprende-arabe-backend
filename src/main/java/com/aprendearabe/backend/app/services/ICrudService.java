package com.aprendearabe.backend.app.services;

import java.util.List;

public interface ICrudService <T> {
	public List<T> getAll();
	public T getById(Long id);
	public T save(T t);
	public void deleteById(Long id);
}
