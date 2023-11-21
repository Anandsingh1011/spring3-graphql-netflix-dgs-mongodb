package com.twc.movie.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.twc.movie.document.Movies;

@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movies, String> {

}
