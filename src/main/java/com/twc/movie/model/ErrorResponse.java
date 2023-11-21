package com.twc.movie.model;

import lombok.*;

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
