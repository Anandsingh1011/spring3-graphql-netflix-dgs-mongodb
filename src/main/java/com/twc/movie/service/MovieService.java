package com.twc.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.twc.generated.types.MovieInput;
import com.twc.movie.document.Comments;
import com.twc.movie.document.Movies;
import com.twc.movie.mapper.CommentsMapper;
import com.twc.movie.mapper.MovieMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieService {

	@Autowired
	ReactiveMongoTemplate mongoTemplate;

	private static final Log log = LogFactory.getLog(MovieService.class);

	@Autowired
	MovieMapper movieMapper;

	@Autowired
	CommentsMapper commentsMapper;

	public Mono<List<com.twc.generated.types.Movie>> getMovies(MovieInput searchMovie, int page, int size) {

		//Pageable pageable = PageRequest.of(page, size);

		Query query = getMovieQuery(searchMovie);//.with(pageable);

		Flux<Movies> fluxMovies = mongoTemplate.find(query, Movies.class, "movies");
		
		
		
		Mono<List<Movies>> movieList = fluxMovies.collectList();

		return movieMapper.fromMonoList(movieList);

	}

	public Mono<List<com.twc.generated.types.Movie>> getMovies2(MovieInput searchMovie, int page, int size) {

		Pageable pageable = PageRequest.of(1, 3);

		Query query = getMovieQuery(searchMovie).with(pageable);

		Mono<List<Movies>> movieList = mongoTemplate.find(query, Movies.class, "movies").collectList();

		Mono<List<Comments>> commentsList = mongoTemplate
				.find(getCommentsQuery(searchMovie, movieList), Comments.class, "comments").collectList();

		return Mono.zip(movieList, commentsList).map(d -> {
			List<Movies> moviList = d.getT1();

			d.getT2();

			moviList.forEach(x -> {

				List<Comments> commant = d.getT2().stream().filter(o -> o.getMovie_id().equalsIgnoreCase(x.getId()))
						.collect(Collectors.toList());
				x.setComments(commant);

				System.out.println(commant.size() + " - id : " + x.getId());

				commant.forEach(xw -> {
					System.out.println(xw.getId() + " mId : " + xw.getMovie_id());
				});

			});

			List<com.twc.generated.types.Movie> m = movieMapper.mapToMoviesList(moviList);
			return m;

		});

	}

	public Flux<com.twc.generated.types.Movie> mergeFinalMovies(MovieInput searchMovie, int page, int size) {

		Pageable pageable = PageRequest.of(1, 3);

		Query query = getMovieQuery(searchMovie).with(pageable);

		Flux<Movies> movieList = mongoTemplate.find(query, Movies.class, "movies");

		Flux<Comments> commentsList = mongoTemplate.find(getCommentsQueryObj(searchMovie), Comments.class, "comments");

		// Merging Parent and Child Flux based on the common ID
		Flux<Movies> finalFlux = movieList.flatMap(parent -> commentsList
				.filter(child -> child.getMovie_id() == parent.getId()).collectList().map(children -> {
					parent.setComments(children);
					return parent;
				}));

		Flux<com.twc.generated.types.Movie> m = movieMapper.fromFlux(finalFlux);
		return m;
	}

	private Query getMovieQuery(MovieInput searchMovie) {
		Query query = new Query();
		int startDate = Integer.parseInt(searchMovie.getYearStart());
		int endDate = Integer.parseInt(searchMovie.getYearEnd());

		query.addCriteria(Criteria.where("year").lt(endDate).gt(startDate));
		query.with(Sort.by(Sort.Direction.ASC, "year"));

		return query;
	}

	private Query getCommentsQueryObj(MovieInput searchMovie) {

		ObjectId obId = new ObjectId("573a1390f29313caabcd446f");
		Query queryComments = new Query();
		queryComments.addCriteria(Criteria.where("movie_id").is(obId));

		return queryComments;
	}

	private Query getCommentsQuery(MovieInput searchMovie, Mono<List<Movies>> movieList) {

		movieList.subscribe(x -> {
			x.forEach(y -> {
				// y.getId()
			});
		});
//		ObjectId[] objarray = new ObjectId[ids.length];
//
//		for(int i=0;i<ids.length;i++)
//		{
//		    objarray[i] = new ObjectId(ids[i]);
//		}

		ObjectId obId = new ObjectId("573a1390f29313caabcd446f");
		Query queryComments = new Query();
		queryComments.addCriteria(Criteria.where("movie_id").is(obId));

		return queryComments;
	}

}
