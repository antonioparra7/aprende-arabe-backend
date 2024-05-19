package com.aprendearabe.backend.app.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingRequest {
	private Integer score;
	private Long lessonId;
    private Long userId;
}
