package com.twc.movie.model;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author Anand Singh
 *
 * 
 */
public interface MovieRepository extends ReactiveMongoRepository<Movies, String> {

}
