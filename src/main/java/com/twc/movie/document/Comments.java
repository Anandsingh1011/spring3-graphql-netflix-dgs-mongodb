package com.twc.movie.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Document("comments")
@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Comments {

	@Id
	private String id;
	private String name;
	private String email;
	private String movie_id;
	private String text;
}

