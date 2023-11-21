package com.twc.movie.expection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.netflix.graphql.dgs.client.GraphQLError;
import com.netflix.graphql.types.errors.TypedGraphQLError;

import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {

   @Override
   public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
      if (handlerParameters.getException() instanceof MovieExpection) {
         Map<String, Object> debugInfo = new HashMap<>();
         debugInfo.put("somefield", "somevalue");

         TypedGraphQLError graphqlError = TypedGraphQLError.newInternalErrorBuilder()
                 .message("This custom thing went wrong!")
                 .debugInfo(debugInfo)
                 .path(handlerParameters.getPath()).build();

         DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult()
                 .error(graphqlError)
                 .build();

         return CompletableFuture.completedFuture(result);
      } else {
         return DataFetcherExceptionHandler.super.handleException(handlerParameters);
      }
   }
}