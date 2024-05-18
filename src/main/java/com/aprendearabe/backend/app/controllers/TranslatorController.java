package com.aprendearabe.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.controllers.requests.TranslatorRequest;
import com.aprendearabe.backend.app.services.TranslatorService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/translator")
public class TranslatorController {
	@Autowired
	private TranslatorService translatorService;
	
	@PostMapping("/es-ar")
	public ResponseEntity<String> translateEsToAr(@RequestBody TranslatorRequest request){
		try {
            String translation = translatorService.translateEsToAr(request.getText());
            return ResponseEntity.ok(translation);
        } catch (Exception e) {
            return new ResponseEntity<String>(String.format("Error al traducir la palabra: ".concat(request.getText())),HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}
