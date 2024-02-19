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

import com.aprendearabe.backend.app.models.entities.Test;
import com.aprendearabe.backend.app.models.entities.Qualification;
import com.aprendearabe.backend.app.models.entities.User;
import com.aprendearabe.backend.app.services.TestService;
import com.aprendearabe.backend.app.services.QualificationService;
import com.aprendearabe.backend.app.services.UserService;

import jakarta.validation.ConstraintViolationException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/qualifications")
public class QualificationController {
	@Autowired
	private TestService testService;
	@Autowired
	private UserService userService;
	@Autowired
	private QualificationService qualificationService;

	@GetMapping("")
	public ResponseEntity<?> getQualifications() {
		List<Qualification> qualifications = null;
		try {
			qualifications = qualificationService.getAll();
			return new ResponseEntity<List<Qualification>>(qualifications, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/testId/{id}")
	public ResponseEntity<?> getQualificationsByTestId(@PathVariable Long id) {
		List<Qualification> qualifications = null;
		try {
			if (testService.getById(id) != null) {
				qualifications = qualificationService.getAllByTestId(id);
				return new ResponseEntity<List<Qualification>>(qualifications, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(String.format("Test con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/userId/{id}")
	public ResponseEntity<?> getQualificationsByUserId(@PathVariable Long id) {
		List<Qualification> qualifications = null;
		try {
			if (userService.getById(id) != null) {
				qualifications = qualificationService.getAllByUserId(id);
				return new ResponseEntity<List<Qualification>>(qualifications, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(String.format("User con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("testId/{testId}/userId/{userId}")
	public ResponseEntity<?> getQualificationByLessonIdAndUserId(@PathVariable Long testId, @PathVariable Long userId) {
		Qualification qualification = null;
		try {
			if (testService.getById(testId)!=null && userService.getById(userId)!=null) {
				qualification = qualificationService.getByTestIdAndUserId(testId, userId);
				return new ResponseEntity<Qualification>(qualification, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(String.format("Rating con testId: %d y con userId: %d no existe en base de datos", testId, userId),
						HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getQualificationById(@PathVariable Long id) {
		Qualification qualification = null;
		try {
			qualification = qualificationService.getById(id);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (qualification != null) {
			return new ResponseEntity<Qualification>(qualification, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format("Qualification con id: %d no existe en base de datos", id),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> createQualification(@RequestParam Double score, @RequestParam Long testId,
			@RequestParam Long userId) throws IOException {
		Qualification qualificationAdded = null;
		try {
			Test test = testService.getById(testId);
			User user = userService.getById(userId);
			if (test != null && user != null) {
				Qualification qualification = new Qualification(score, test, user);
				qualificationAdded = qualificationService.save(qualification);
			} 
			else {
				if(test==null && user==null) {
					return new ResponseEntity<String>(String.format("Test con id: %d y User con id: %d no existen en base de datos", testId, userId),
							HttpStatus.NOT_FOUND);
				}
				else if(test ==null) {
					return new ResponseEntity<String>(String.format("Test con id: %d no existe en base de datos", testId),
							HttpStatus.NOT_FOUND);
				}
				else {
					return new ResponseEntity<String>(String.format("User con id: %d no existe en base de datos", userId),
							HttpStatus.NOT_FOUND);
				}
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.BAD_REQUEST);
		}
		if (qualificationAdded != null && qualificationAdded.getId() > 0) {
			return new ResponseEntity<String>("Qualification añadida con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir qualification", HttpStatus.NOT_FOUND);
		}
	}

	// Añadir metodo update

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteQualification(@PathVariable Long id) {
		try {
			Qualification qualification = qualificationService.getById(id);
			if (qualification == null) {
				return new ResponseEntity<String>(String.format("Qualification con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
			qualificationService.deleteById(id);
			return new ResponseEntity<String>("Qualification eliminada con éxito", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
