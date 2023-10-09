package com.twc.movie.model;

import lombok.*;

/**
 * @author Anand Singh
 *
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorResponse {

    private String code;
    private String message;
    private String description;
    private Object errors;
}
