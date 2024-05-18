package com.aprendearabe.backend.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aprendearabe.backend.app.models.entities.User;
import com.aprendearabe.backend.app.models.repositories.IUserRepository;

@Service
public class GmailService {
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IUserRepository userRepository;
	
	public void sendEmail(String to,String subject, String body) {
		User user = userRepository.findByEmail(to).orElse(null);
		if(user!=null) {
			//mandamos el mensaje
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("antonioparra00@gmail.com");
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			emailSender.send(message);
			//guardamos la nueva contraseña
			int startIndex = body.indexOf("es ") + 4;
	        String substring = body.substring(startIndex);
	        String password = substring.replaceAll("'", "");
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
		}	
	}
}
