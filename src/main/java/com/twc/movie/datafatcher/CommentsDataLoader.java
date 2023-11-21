package com.twc.movie.datafatcher;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.netflix.graphql.dgs.DgsDataLoader;
import com.twc.movie.document.Comments;
import com.twc.movie.mapper.CommentsMapper;
import com.twc.movie.service.MovieService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DgsDataLoader(name = "comments")
public class CommentsDataLoader implements MappedBatchLoader<String, List<com.twc.generated.types.Comments>> {

	@Autowired
	private MovieService movieService;

	@Autowired
	ReactiveMongoTemplate mongoTemplate;

	@Autowired
	CommentsMapper commentsMapper;

	@Override
	public CompletionStage<Map<String, List<com.twc.generated.types.Comments>>> load(Set<String> keys) {
		Mono<Map<String, Collection<com.twc.generated.types.Comments>>> line;
		try {
			line = getComments(keys).collectMultimap(com.twc.generated.types.Comments::getMovie_id, items -> items);

			return line.map(m -> m.entrySet().stream()
					.collect(Collectors.toMap(e -> e.getKey(), e -> List.copyOf(e.getValue())))).toFuture();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return CompletableFuture.supplyAsync(() -> null);
	}

	private Flux<com.twc.generated.types.Comments> getComments(Set<String> movieIdList) {

		List<ObjectId> ObjectIdList = movieIdList.stream().map(x -> new ObjectId(x)).collect(Collectors.toList());
		// ObjectId obId = new ObjectId("573a1390f29313caabcd446f");
		Query queryComments = new Query();
		queryComments.addCriteria(Criteria.where("movie_id").in(ObjectIdList));

		Flux<Comments> commentsList = mongoTemplate.find(queryComments, Comments.class, "comments");

		Flux<com.twc.generated.types.Comments> commentsFf = commentsList.switchMap(comm -> {
			com.twc.generated.types.Comments comments = commentsMapper.mapToComments(comm);

			return Flux.just(comments);
		});

		return commentsFf;
	}

}