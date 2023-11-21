package com.twc.movie.datafatcher;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;


import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.context.ReactiveDgsContext;
import com.twc.generated.DgsConstants;
import com.twc.generated.types.Comments;
import com.twc.generated.types.Match;
import com.twc.generated.types.MetaData;
import com.twc.generated.types.Movie;
import com.twc.generated.types.MovieInput;
import com.twc.generated.types.Page;
import com.twc.generated.types.SortBy;
import com.twc.movie.config.MovieContext;
import com.twc.movie.config.MovieReactiveDgsContext;
import com.twc.movie.config.PagingList;
import com.twc.movie.expection.MovieExpection;
import com.twc.movie.mapper.MovieMapper;
import com.twc.movie.service.MovieService;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@DgsComponent
public class MovieDatafatcher {

	@Autowired
	private MovieService movieService;

	@Autowired
	private MovieReactiveDgsContext ctx;

	@Autowired
	MovieMapper movieMapper;

	@DgsQuery
	@Observed(name = "movies.get")
	// @DgsData(parentType = "Query", field = "movies")
	public Mono<List<Movie>> movies(@InputArgument MovieInput searchMovie, @InputArgument SortBy sortBy,
			@InputArgument Boolean subTitle, @InputArgument List<Match> match, @InputArgument int size,
			@InputArgument int page, DataFetchingEnvironment dfe) {

		Mono<List<Movie>> movieList = movieService.getMovies(searchMovie, size, page);
		
		
		
		Mono<List<Movie>> movieListRet = movieList.flatMap(x -> {

			PagingList<Movie> pl = new PagingList<Movie>(x, String.valueOf(size), String.valueOf(page), 25);

			if (!CollectionUtils.isEmpty(x)) {

				Page p = Page.newBuilder().totalElements(pl.getTotalElements()).totalPages(pl.getTotalPages())
						.size(pl.getRowsPerPage()).number(page).build();

				MetaData metaData = MetaData.newBuilder().page(p).build();
				MovieContext context = ReactiveDgsContext.getCustomContext(dfe);
				context.setMetaData(metaData);
				context.setMetaData(MetaData.newBuilder().page(p).build());
			}

			return Mono.just(pl.getCurrentPageElements());

		});

		return movieListRet;
	}

	@DgsData(parentType = DgsConstants.MOVIE.TYPE_NAME, field = DgsConstants.MOVIE.Comments)
	public CompletableFuture<List<Comments>> comments(DataFetchingEnvironment dfe) {

		DataLoader<String, List<com.twc.generated.types.Comments>> dataLoader = dfe.getDataLoader("comments");

		Movie movie = dfe.getSource();
		String keyS = movie.getId();

		return dataLoader.load(keyS);

	}

//	// @Observed(name = "movies.get")
//	// @DgsData(parentType = "Query", field = "movies")
//	public Mono<List<Movie>> movies1(MovieInput searchMovie, DataFetchingEnvironment dfe, @InputArgument int size,
//			@InputArgument int page) {
//
//		Mono<List<Movie>> movieList = movieService.getMovies(searchMovie, size, page);
//		// Mono<List<Movie>> movieList =
//		// movieService.mergeFinalMovies(searchMovie,size,page).collectList();
//
//		Pageable pageable = PageRequest.of(page, size);
//		movieList.subscribe(x -> {
//			org.springframework.data.domain.Page<Movie> pg = PageableExecutionUtils.getPage(x, pageable,
//					() -> x.size());
//
//			MovieContext context = ReactiveDgsContext.getCustomContext(dfe);
//
//			Page p = Page.newBuilder().size(pg.getSize()).totalElements((int) pg.getTotalElements())
//					.totalPages(pg.getTotalPages()).number(pg.getNumber()).build();
//
//			context.setMetaData(MetaData.newBuilder().page(p).build());
//		});
//
//		return movieList;
//	}

	// @Observed(name = "movies.get")
	@DgsData(parentType = "Query", field = "metaData")
	public DataFetcherResult<MetaData> metaData(DataFetchingEnvironment dfe) {

		MovieContext context = ReactiveDgsContext.getCustomContext(dfe);
		MetaData metaData = context.getMetaData();

		context.setMetadataRequested(true);

		return DataFetcherResult.<MetaData>newResult().data(metaData).build();
		// Mono.just(MetaData.newBuilder().page(p).build());
		// return Mono.just(context.getMetaData());
	}
}
