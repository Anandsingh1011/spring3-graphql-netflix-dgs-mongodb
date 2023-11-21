package com.twc.movie.model;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * <h2>TenantDatasource</h2>
 *
 * @author Anand Singh
 * <p>
 * Description:
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantDatasource {

    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    private String database;
    @NotNull
    private int port;
    @NotNull
    @NotBlank
    private String host;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;

}
