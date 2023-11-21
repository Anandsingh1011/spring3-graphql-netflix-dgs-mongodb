package com.twc.movie.configuration;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.graphql.dgs.context.ReactiveDgsContext;
import com.twc.movie.config.MovieContext;

import graphql.ExecutionResultImpl;
import graphql.GraphQLContext;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimplePerformantInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;

@Component
public class AppInstrumentation extends SimplePerformantInstrumentation {

	private static final Logger log = LoggerFactory.getLogger(AppInstrumentation.class);

	private static final Logger REQ_LOG = LoggerFactory.getLogger("com.twc.movie.received");

	private final Clock clock = Clock.systemDefaultZone();;

	@Override
	public InstrumentationState createState() {
		return new TracingState();
	}

	@Override
	public InstrumentationContext<graphql.ExecutionResult> beginExecution(
			InstrumentationExecutionParameters parameters) {
		Instant start = Instant.now(clock);

		TracingState tracingState = parameters.getInstrumentationState();
		// TracingState tracingState = new TracingState();
		tracingState.startTime = System.currentTimeMillis();
		tracingState.start = start;

		// if(REQ_LOG.isDebugEnabled()) {
		boolean log = true;
		if (parameters.getQuery() != null && parameters.getQuery().contains("IntrospectionQuery"))
			log = false;
		if (log) {
			REQ_LOG.debug("<---- Incomming Graphl request ---->");
			REQ_LOG.debug("{} for Variables:{}", parameters.getQuery(), parameters.getVariables());
			REQ_LOG.debug("<---- END Incomming Graphl request ---->");
		}
		// }

		return super.beginExecution(parameters);
	}

	@Override
	public @NotNull CompletableFuture<graphql.ExecutionResult> instrumentExecutionResult(
			graphql.ExecutionResult executionResult, InstrumentationExecutionParameters parameters,
			InstrumentationState state) {

		TracingState tracingState = (TracingState) state;
		long end = System.currentTimeMillis();
		// Add Header values
		GraphQLContext context = parameters.getGraphQLContext();

		ReactiveDgsContext reactiveDgsContext = ReactiveDgsContext.from(context);

		HashMap<Object, Object> extensions = new HashMap<>();

		MovieContext obj = (MovieContext) reactiveDgsContext.getCustomContext();
		Map<String, Object> data = executionResult.getData();
		if (obj.isMetadataRequested() && obj.getMetaData() != null) {

			if (data != null && data.get("metaData") == null) {

				// LinkedHashMap<String, Object> map =
				// (LinkedHashMap<String,Object>)data.get("data");
				// if(map.get("metaData")==null)
				data.put("metaData", obj.getMetaData());
			}

		}
		//extensions.put("query", parameters.getQuery());
		//extensions.put("Variables", parameters.getVariables());

		// Duration duration = Duration.between(tracingState.start, Instant.now(clock));
		// log.info("Completed FED DaaS Request Successfully in: {} milli - {}",
		// duration.toMillis(),(end - tracingState.startTime));
		return super.instrumentExecutionResult(new ExecutionResultImpl(data, executionResult.getErrors(), extensions),
				parameters, state);
	}

	static class TracingState implements InstrumentationState {
		long startTime;
		Instant start;
	}
}
