package com.twc.movie.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Anand Singh
 *
 * 
 */
@Document("movies")
@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Movies {

	@Id
	private String id;
	private String plot;
	private String year;
	private String title;
	private String rated;

}
