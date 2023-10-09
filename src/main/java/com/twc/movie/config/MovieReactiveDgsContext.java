package com.twc.movie.config;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.netflix.graphql.dgs.reactive.DgsReactiveCustomContextBuilderWithRequest;

import reactor.core.publisher.Mono;

/**
 * @author Anand Singh
 *
 * 
 */
@Component
public class MovieReactiveDgsContext implements DgsReactiveCustomContextBuilderWithRequest<MovieContext> {

	@Override
	public Mono<MovieContext> build(Map<String, ? extends Object> arg0, HttpHeaders arg1, ServerRequest arg2) {
		return Mono.just(new MovieContext());
	}

}
