package com.aprendearabe.backend.app.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aprendearabe.backend.app.models.entities.Parameter;
import com.aprendearabe.backend.app.services.ParameterService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1/parameters")
public class ParameterController {
	@Autowired
	private ParameterService parameterService;

	@GetMapping("")
	public ResponseEntity<?> getParameters() {
		List<Parameter> parameters = null;
		try {
			parameters = parameterService.getAll();
			return new ResponseEntity<List<Parameter>>(parameters, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getParameterById(@PathVariable Long id) {
		Parameter parameter = null;
		try {
			parameter = parameterService.getById(id);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (parameter != null) {
			return new ResponseEntity<Parameter>(parameter, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format("Parameter con id: %d no existe en base de datos", id),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> createParameter(@RequestParam String name, @RequestParam String provider,
			@RequestParam String endpoint, @RequestParam String host, @RequestParam String keyEndpoint,
			@RequestParam String location, @RequestParam MultipartFile image) throws IOException {
		Parameter parameterAdded = null;
		try {
			Parameter parameter = new Parameter(name, !provider.isEmpty() ? provider : null,
					!endpoint.isEmpty() ? endpoint : null, !host.isEmpty() ? host : null,
					!keyEndpoint.isEmpty() ? keyEndpoint : null, !location.isEmpty() ? location : null,
					!image.isEmpty() ? image.getBytes() : null);
			parameterAdded = parameterService.save(parameter);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (parameterAdded != null && parameterAdded.getId() > 0) {
			return new ResponseEntity<String>("Parameter añadido con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir parameter", HttpStatus.NOT_FOUND);
		}
	}

	// Añadir metodo update

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteParameter(@PathVariable Long id) {
		try {
			Parameter parameter = parameterService.getById(id);
			if (parameter == null) {
				return new ResponseEntity<String>(String.format("Parameter con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
			parameterService.deleteById(id);
			return new ResponseEntity<String>("Parameter eliminado con éxito", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
