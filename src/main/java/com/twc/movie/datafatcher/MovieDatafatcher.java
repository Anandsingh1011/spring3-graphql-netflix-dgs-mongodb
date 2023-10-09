package com.twc.movie.datafatcher;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.twc.generated.types.Movie;
import com.twc.generated.types.MovieInput;
import com.twc.movie.config.MovieReactiveDgsContext;
import com.twc.movie.mapper.MovieMapper;
import com.twc.movie.service.MovieService;

import graphql.schema.DataFetchingEnvironment;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @author Anand Singh
 *
 * 
 */
@Slf4j
@DgsComponent
public class MovieDatafatcher {

	@Autowired
	private MovieService movieService;

	@Autowired
	private MovieReactiveDgsContext ctx;
	
	@Observed(name = "movies.get")
	@DgsData(parentType = "Query", field = "movies")
	public Flux<Movie> movies(MovieInput searchMovie,DataFetchingEnvironment dfe) {

		Flux<Movie> movieList = movieService.getMovies(searchMovie).map(x -> MovieMapper.INSTANCE.mapToMovies(x));
		return movieList;
	}

}
