package com.twc.movie.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.twc.generated.types.Comments;
import com.twc.generated.types.Movie;
import com.twc.movie.document.Movies;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface CommentsMapper {

	CommentsMapper INSTANCE = Mappers.getMapper(CommentsMapper.class);

	// @Mapping(source = "title", target = "title")

	Comments mapToComments(com.twc.movie.document.Comments movie);

	List<Comments> mapToCommentsList(List<com.twc.movie.document.Comments> movie);

	default Flux<Comments> fromFlux(Flux<com.twc.movie.document.Comments> flux) {
		return flux.map(this::mapToComments);
	}

	default Mono<Comments> fromMono(Mono<com.twc.movie.document.Comments> mono) {
		return mono.map(this::mapToComments);
	}
}
