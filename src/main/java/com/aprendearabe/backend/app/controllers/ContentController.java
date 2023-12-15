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

import com.aprendearabe.backend.app.models.entities.Content;
import com.aprendearabe.backend.app.models.entities.Lesson;
import com.aprendearabe.backend.app.services.ContentService;
import com.aprendearabe.backend.app.services.LessonService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/contents")
public class ContentController {
	@Autowired
	private LessonService lessonService;
	@Autowired
	private ContentService contentService;
	
	@GetMapping("")
	public ResponseEntity<?> getContents(){
		List<Content> contents = null;
		try {
			contents = contentService.getAll();
			return new ResponseEntity<List<Content>>(contents,HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getContentById(@PathVariable Long id){
		Content content = null;
		try {
			content = contentService.getById(id);
		}catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (content!=null) {
			return new ResponseEntity<Content>(content,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(String.format("Content con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<String> createContent(@RequestParam String word, @RequestParam String wordTranslate, @RequestParam MultipartFile image, @RequestParam Long lessonId) throws IOException {
		Content contentAdded = null;
		try {
			Lesson lesson = lessonService.getById(lessonId);
			if (lesson != null) {
				Content content = new Content(word, wordTranslate, image.getBytes(), lesson);
				contentAdded = contentService.save(content);
			} else {
				return new ResponseEntity<String>(
						String.format("Lesson con id: %d no existe en base de datos", lessonId), HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (contentAdded != null && contentAdded.getId() > 0) {
			return new ResponseEntity<String>("Content añadida con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir content", HttpStatus.NOT_FOUND);
		}
	}
	
	// Añadir metodo update
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteContent(@PathVariable Long id) {
		try {
			Content content = contentService.getById(id);
			if(content==null) {
				return new ResponseEntity<String>(String.format("Content con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
			}
			contentService.deleteById(id);
			return new ResponseEntity<String>("Content eliminado con éxito",HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}