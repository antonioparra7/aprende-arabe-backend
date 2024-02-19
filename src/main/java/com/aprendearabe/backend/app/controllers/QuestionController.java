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

import com.aprendearabe.backend.app.models.entities.Question;
import com.aprendearabe.backend.app.models.entities.Test;
import com.aprendearabe.backend.app.services.QuestionService;
import com.aprendearabe.backend.app.services.TestService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
	@Autowired
	private TestService testService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("")
	public ResponseEntity<?> getQuestions(){
		List<Question> questions = null;
		try {
			questions = questionService.getAll();
			return new ResponseEntity<List<Question>>(questions,HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/testId/{id}")
	public ResponseEntity<?> getQuestionsByTestId(@PathVariable Long id) {
		List<Question> questions = null;
		try {
			if (testService.getById(id) != null) {
				questions = questionService.getAllByTestId(id);
				return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getQuestionById(@PathVariable Long id){
		Question question = null;
		try {
			question = questionService.getById(id);
		}catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (question!=null) {
			return new ResponseEntity<Question>(question,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(String.format("Question con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<String> createQuestion(@RequestParam String question, @RequestParam String responseA, @RequestParam String responseB, @RequestParam String responseC, @RequestParam String responseD, @RequestParam String responseCorrect,@RequestParam MultipartFile image, @RequestParam Long testId) throws IOException {
		Question questionAdded = null;
		try {
			Test test = testService.getById(testId);
			if (test != null) {
				Question q = new Question(question, responseA, responseB, responseC, responseD, responseCorrect, image.getBytes(), test);
				questionAdded = questionService.save(q);
			} else {
				return new ResponseEntity<String>(
						String.format("Test con id: %d no existe en base de datos", testId), HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (questionAdded != null && questionAdded.getId() > 0) {
			return new ResponseEntity<String>("Question añadida con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir question", HttpStatus.NOT_FOUND);
		}
	}
	
	// Añadir metodo update
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
		try {
			Question question = questionService.getById(id);
			if(question==null) {
				return new ResponseEntity<String>(String.format("Question con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
			}
			questionService.deleteById(id);
			return new ResponseEntity<String>("Question eliminada con éxito",HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
