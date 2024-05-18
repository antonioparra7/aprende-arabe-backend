package com.aprendearabe.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.controllers.requests.GmailRequest;
import com.aprendearabe.backend.app.services.GmailService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/gmail")
public class GmailController {
	@Autowired
	private GmailService gmailService;
	
	@PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody GmailRequest gmailRequest) {
        try {
			gmailService.sendEmail(gmailRequest.getTo(), gmailRequest.getSubject(), gmailRequest.getBody());
			return new ResponseEntity<GmailRequest>(gmailRequest,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(String.format("Error al enviar correo electrónico: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
