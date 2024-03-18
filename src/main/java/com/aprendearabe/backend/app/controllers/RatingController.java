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

import com.aprendearabe.backend.app.models.entities.Lesson;
import com.aprendearabe.backend.app.models.entities.Rating;
import com.aprendearabe.backend.app.models.entities.User;
import com.aprendearabe.backend.app.services.LessonService;
import com.aprendearabe.backend.app.services.RatingService;
import com.aprendearabe.backend.app.services.UserService;

import jakarta.validation.ConstraintViolationException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {
	@Autowired
	private LessonService lessonService;
	@Autowired
	private UserService userService;
	@Autowired
	private RatingService ratingService;

	@GetMapping("")
	public ResponseEntity<?> getRatings() {
		List<Rating> ratings = null;
		try {
			ratings = ratingService.getAll();
			return new ResponseEntity<List<Rating>>(ratings, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lessonId/{id}")
	public ResponseEntity<?> getRatingsByLessonId(@PathVariable Long id) {
		List<Rating> ratings = null;
		try {
			if (lessonService.getById(id) != null) {
				ratings = ratingService.getAllByLessonId(id);
				return new ResponseEntity<List<Rating>>(ratings, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(String.format("Lesson con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/userId/{id}")
	public ResponseEntity<?> getRatingsByUserId(@PathVariable Long id) {
		List<Rating> ratings = null;
		try {
			if (userService.getById(id) != null) {
				ratings = ratingService.getAllByUserId(id);
				return new ResponseEntity<List<Rating>>(ratings, HttpStatus.OK);
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
	
	@GetMapping("lessonId/{lessonId}/userId/{userId}")
	public ResponseEntity<?> getRatingByLessonIdAndUserId(@PathVariable Long lessonId, @PathVariable Long userId) {
		Rating rating = null;
		try {
			if (lessonService.getById(lessonId)!=null && userService.getById(userId)!=null) {
				rating = ratingService.getByLessonIdAndUserId(lessonId, userId);
				return new ResponseEntity<Rating>(rating, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(String.format("Rating con lessonId: %d y con userId: %d no existe en base de datos", lessonId, userId),
						HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRatingById(@PathVariable Long id) {
		Rating rating = null;
		try {
			rating = ratingService.getById(id);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (rating != null) {
			return new ResponseEntity<Rating>(rating, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format("Rating con id: %d no existe en base de datos", id),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> createRating(@RequestParam Integer score, @RequestParam Long lessonId,
			@RequestParam Long userId) throws IOException {
		Rating ratingAdded = null;
		try {
			if (ratingService.getByLessonIdAndUserId(lessonId, userId)==null) {
				Lesson lesson = lessonService.getById(lessonId);
				User user = userService.getById(userId);
				if (lesson != null && user != null) {
					Rating ratingExist = ratingService.getByLessonIdAndUserId(lessonId,userId);
					if(ratingExist!=null) {
						ratingExist.setScore(score);
						ratingAdded = ratingService.save(ratingExist);
					}
					else {
						Rating rating = new Rating(score, lesson, user);
						ratingAdded = ratingService.save(rating);
					}
				} 
				else {
					if(lesson==null && user==null) {
						return new ResponseEntity<String>(String.format("Lesson con id: %d y User con id: %d no existen en base de datos", lessonId, userId),
								HttpStatus.NOT_FOUND);
					}
					else if(lesson ==null) {
						return new ResponseEntity<String>(String.format("Lesson con id: %d no existe en base de datos", lessonId),
								HttpStatus.NOT_FOUND);
					}
					else {
						return new ResponseEntity<String>(String.format("User con id: %d no existe en base de datos", userId),
								HttpStatus.NOT_FOUND);
					}
				}
			}
			else {
				return new ResponseEntity<String>(String.format("Rating existente en base de datos"),
						HttpStatus.CONFLICT);
			}
			
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (ratingAdded != null && ratingAdded.getId() > 0) {
			return new ResponseEntity<String>("Rating añadido con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir rating", HttpStatus.NOT_FOUND);
		}
	}

	// Añadir metodo update

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRating(@PathVariable Long id) {
		try {
			Rating rating = ratingService.getById(id);
			if (rating == null) {
				return new ResponseEntity<String>(String.format("Rating con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
			ratingService.deleteById(id);
			return new ResponseEntity<String>("Rating eliminado con éxito", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
