package com.twc.movie.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.twc.generated.types.Movie;
import com.twc.movie.document.Movies;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring", uses = {
		CommentsMapper.class })
public interface MovieMapper {

	MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

	@Mapping(source = "title", target = "title")

	Movie mapToMovies(Movies movie);

	List<Movie> mapToMoviesList(List<Movies> movie);

	default Flux<Movie> fromFlux(Flux<Movies> flux) {
		return flux.map(this::mapToMovies);
	}

	default Mono<Movie> fromMono(Mono<Movies> mono) {
		return mono.map(this::mapToMovies);
	}
	
	default Mono<List<Movie>> fromMonoList(Mono<List<Movies>> mono) {
		return mono.map(this::mapToMoviesList);
	}
}
