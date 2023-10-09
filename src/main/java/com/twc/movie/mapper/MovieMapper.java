package com.twc.movie.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.twc.generated.types.Movie;
import com.twc.movie.model.Movies;

import reactor.core.publisher.Flux;

/**
 * @author Anand Singh
 *
 * 
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {

	MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

	@Mapping(source = "title", target = "title")
	
	Movie mapToMovies(Movies movie);

	default Flux<Movie> fromFlux(Flux<Movies> flux) {
		return flux.map(this::mapToMovies);
	}
}
