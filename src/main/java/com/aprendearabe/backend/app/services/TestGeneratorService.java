package com.aprendearabe.backend.app.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aprendearabe.backend.app.models.entities.Content;
import com.aprendearabe.backend.app.models.entities.Lesson;
import com.aprendearabe.backend.app.models.entities.Level;
import com.aprendearabe.backend.app.models.entities.Question;
import com.aprendearabe.backend.app.models.entities.Test;
import com.aprendearabe.backend.app.models.entities.Theme;
import com.aprendearabe.backend.app.models.repositories.IContentRepository;
import com.aprendearabe.backend.app.models.repositories.ILessonRepository;
import com.aprendearabe.backend.app.models.repositories.ILevelRepository;
import com.aprendearabe.backend.app.models.repositories.IQuestionRepository;
import com.aprendearabe.backend.app.models.repositories.ITestRepository;
import com.aprendearabe.backend.app.models.repositories.IThemeRepository;

@Service
public class TestGeneratorService {
	@Autowired
	private ILevelRepository levelRepository;
	@Autowired
	private IThemeRepository themeRepository;
	@Autowired
	private ILessonRepository lessonRepository;
	@Autowired
	private IContentRepository contentRepository;
	@Autowired
	private ITestRepository testRepository;
	@Autowired
	private IQuestionRepository questionRepository;
	private Random rand = new Random();
	
	public void testGenerator() {
		List<Level> levels = levelRepository.findAll();
		if (levels.size() > 0) {
			for (Level level : levels) {
				List<Theme> themes = themeRepository.findAllByLevelId(level.getId());
				if (themes.size() > 0) {
					for (Theme theme : themes) {
						Integer contadorTests = 0;
						List<Lesson> lessons = lessonRepository.findAllByThemeId(theme.getId());
						List<Content> contents = new ArrayList<>();
						if (lessons.size() > 0) {
							for (Lesson lesson : lessons) {
								List<Content> contentsLesson = contentRepository.findAllByLessonId(lesson.getId());
								if (contentsLesson.size() > 0) {
									contentsLesson.forEach(c -> contents.add(c));
								}
							}
							while (contadorTests < 2) {
								String nameTest = String.format("%s - %d", theme.getName(),contadorTests+1);
								Test test = new Test(nameTest, level);
								testRepository.save(test);
								Integer contadorQuestions = 0;
								while (contadorQuestions<10) {
									Question question = null;
									if(contents.size()<10) {
										contadorQuestions+=10-contents.size();
									}
									Integer randomNum = rand.nextInt(3);
									switch(randomNum) {
									// Pregunta palabra arabe => 4 opciones en español
									case 0:
										question = questionAr(test,contents);
										break;
									// Pregunta palabra español => 4 opciones en arabe
									case 1:
										question = questionEs(test,contents);
										break;
									// Pregunta imagen => 4 opciones en arabe
									case 2:
										question = questionImage(test,contents);
										break;
									}
									questionRepository.save(question);
									contadorQuestions++;
								}
								contadorTests++;
							}
						}

					}
				}
			}
		}
	}
	// Pregunta palabra arabe => 4 opciones en español
	public Question questionAr(Test test, List<Content> contents) {
		String wordAr = "";
		byte[] imageAr = null;
		List<String> responses = new ArrayList<>();
		Collections.shuffle(contents);
		List<Content> randomContents = contents.subList(0, 4);
		Integer correctContent = rand.nextInt(4);
		String responseCorrect = (correctContent == 0) ? "A" : (correctContent == 1) ? "B" : (correctContent == 2) ? "C" : "D";
		for (int i=0; i<4; i++) {
			if(i==correctContent) {
				wordAr=randomContents.get(i).getWordTranslate();
				imageAr=randomContents.get(i).getImage();
			}
			responses.add(randomContents.get(i).getWord());
		}
		String question = "Seleccione cual de las siguientes opciones corresponde con la palabra " + wordAr;
		return new Question(question,responses.get(0), responses.get(1), responses.get(2), responses.get(3), 
				responseCorrect, imageAr, test);
	}
	// Pregunta palabra español => 4 opciones en arabe
	public Question questionEs(Test test, List<Content> contents) {
		String wordEs = "";
		byte[] imageEs = null;
		List<String> responses = new ArrayList<>();
		Collections.shuffle(contents);
		List<Content> randomContents = contents.subList(0, 4);
		Integer correctContent = rand.nextInt(4);
		String responseCorrect = (correctContent == 0) ? "A" : (correctContent == 1) ? "B" : (correctContent == 2) ? "C" : "D";
		for (int i=0; i<4; i++) {
			if(i==correctContent) {
				wordEs=randomContents.get(i).getWord();
				imageEs=randomContents.get(i).getImage();
			}
			responses.add(randomContents.get(i).getWordTranslate());
		}
		return new Question(String.format("¿Cuál de las siguientes opciones corresponde con la palabra '%s'?",wordEs), responses.get(0), 
				responses.get(1), responses.get(2), responses.get(3), responseCorrect, imageEs, test);
	}
	// Pregunta imagen => 4 opciones en arabe
	public Question questionImage(Test test, List<Content> contents) {
		byte[] imageAr = null;
		List<String> responses = new ArrayList<>();
		Collections.shuffle(contents);
		List<Content> randomContents = contents.subList(0, 4);
		Integer correctContent = rand.nextInt(4);
		String responseCorrect = (correctContent == 0) ? "A" : (correctContent == 1) ? "B" : (correctContent == 2) ? "C" : "D";
		for (int i=0; i<4; i++) {
			if(i==correctContent) {
				imageAr=randomContents.get(i).getImage();
			}
			responses.add(randomContents.get(i).getWordTranslate());
		}
		return new Question("¿Cuál de las siguientes opciones corresponde con la imagen?", responses.get(0), 
				responses.get(1), responses.get(2), responses.get(3), responseCorrect, imageAr, test);
	}
}
