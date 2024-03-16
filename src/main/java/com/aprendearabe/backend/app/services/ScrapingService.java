package com.aprendearabe.backend.app.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aprendearabe.backend.app.models.entities.Content;
import com.aprendearabe.backend.app.models.entities.Lesson;
import com.aprendearabe.backend.app.models.entities.Level;
import com.aprendearabe.backend.app.models.entities.Theme;
import com.aprendearabe.backend.app.utils.Utils;

@Service
public class ScrapingService {
	@Autowired
	private LevelService levelService;
	@Autowired
	private ThemeService themeService;
	@Autowired
	private LessonService lessonService;
	@Autowired
	private ContentService contentService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public void insertThemes() {
		try {
			Level level = levelService.getById(1L);
			Document webHtml = Jsoup.connect("https://www.goethe-verlag.com/book2/_VOCAB/ES/ESAR/ESAR.HTM").get();
			Elements themesHtml = webHtml.getElementsByClass("col-sm-3");
			for (Element themeHtml : themesHtml) {
				String name = themeHtml.getElementsByClass("Stil36").text();
				if (name != null && !name.isEmpty()) {
					String imgSrc = themeHtml.select("img").first().attr("src");
					byte[] image = Utils.convertImg(imgSrc);
					if (image != null) {
						Theme theme = new Theme(name, image, level);
						Theme themeSaved = themeService.save(theme);
						if (themeSaved != null) {
							String urlLesson = themeHtml.select("a").first().attr("href");
							insertLessons(urlLesson, themeSaved);
						}
					}
				}
			}
			log.info("Tematicas insertadas correctamente");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private void insertLessons(String url, Theme theme) {
		try {
			Document webHtml = Jsoup.connect(url).get();
			Integer count = -1;
			Integer numberActualLesson = 1;
			Lesson actualLesson = null;
			Elements lessonsHtml = webHtml.getElementsByClass("col-sm-3");
			for (Element lessonHtml : lessonsHtml) {
				if (count == -1 || count == 10) {
					actualLesson = lessonService.save(new Lesson(theme.getName() + " " + numberActualLesson, theme));
					count = 0;
					numberActualLesson++;
				}
				String word = lessonHtml.getElementsByClass("Stil36").text().replaceAll("(?i)(la|el)\\s+","").trim();
				word = word.substring(0,1).toUpperCase()+word.substring(1);
				String word_translate = lessonHtml.getElementsByClass("Stil46").text();
				String imgSrc = lessonHtml.select("img").first().attr("src");
				;
				byte[] image = Utils.convertImg(imgSrc);
				if (image != null) {
					Content content = new Content(word, word_translate, image, actualLesson);
					contentService.save(content);
				}
				count++;
			}
			log.info("Lecciones insertadas correctamente");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
