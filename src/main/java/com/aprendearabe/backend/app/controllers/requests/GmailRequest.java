package com.aprendearabe.backend.app.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GmailRequest {
	private String to;
	private String subject;
	private String body;
}
