package com.twc.movie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.twc.generated.types.MovieInput;
import com.twc.movie.model.Movies;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Anand Singh
 *
 * 
 */
@Slf4j
@Service
public class MovieService {

	@Autowired
	ReactiveMongoTemplate mongoTemplate;

	public Flux<Movies> getMovies(MovieInput searchMovie) {

		Query query = new Query();
		int startDate = Integer.parseInt(searchMovie.getYearStart());
		int endDate = Integer.parseInt(searchMovie.getYearEnd());

		query.addCriteria(Criteria.where("year").lt(endDate).gt(startDate));
		query.with(Sort.by(Sort.Direction.ASC, "year"));

		Flux<Movies> moviesList = mongoTemplate.find(query, Movies.class);

		log.info("moviesList : " + moviesList.doOnNext(x -> System.out.println(x.getId())));
		
		List<Movies> list3 = new ArrayList<>();
		moviesList.collectList().subscribe(list3::addAll);
		list3.forEach(x -> log.info(""+x));
		
		return moviesList;

		// return mongoTemplate.findAll( Movies.class);

	}

	public Flux<Movies> getMoviesPage(MovieInput searchMovie) {

		Query query = new Query();
		int startDate = Integer.parseInt(searchMovie.getYearStart());
		int endDate = Integer.parseInt(searchMovie.getYearEnd());

		query.addCriteria(Criteria.where("year").lt(endDate).gt(startDate));
		query.with(Sort.by(Sort.Direction.ASC, "year"));

		final Pageable pageableRequest = PageRequest.of(0, 2);
		query.with(pageableRequest);

		mongoTemplate.find(query, Movies.class);

		return mongoTemplate.find(query, Movies.class);

		// return mongoTemplate.findAll( Movies.class);

	}

	public Flux<Movies> getMoviesBase(MovieInput searchMovie) {

		Query query = new Query();
		int startDate = Integer.parseInt(searchMovie.getYearStart());
		int endDate = Integer.parseInt(searchMovie.getYearEnd());

		query.addCriteria(Criteria.where("year").lt(endDate).gt(startDate));
		query.with(Sort.by(Sort.Direction.ASC, "year"));

		return mongoTemplate.find(query, Movies.class);

		// return mongoTemplate.findAll( Movies.class);

	}

	public Mono<Movies> getMovie() {
		Query query = new Query();

		query.addCriteria(Criteria.where("title").is("The Land Beyond the Sunset"));
		return mongoTemplate.findOne(query, Movies.class);

	}
}
