package com.twc.movie.filter;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twc.movie.config.AppConstant;
import com.twc.movie.config.MovieContext;
import com.twc.movie.service.MovieService;

import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RequestMonitorWebFilter implements WebFilter {

	private static final Log LOGGER = LogFactory.getLog(RequestMonitorWebFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		long startTime = System.currentTimeMillis();
		return chain.filter(exchange)
				/**
				 * Preparing context for the Tracer Span used in TracerConfiguration
				 */
				.contextWrite(context -> {
					// ContextSnapshot.setThreadLocalsFrom(context,
					// ObservationThreadLocalAccessor.KEY);

					String tenantKey = exchange.getRequest().getHeaders().getFirst(AppConstant.VENDOR_HEADER_KEY);

					String meta_senderapp = exchange.getRequest().getHeaders().getFirst("meta-senderapp");
					String meta_transid = exchange.getRequest().getHeaders().getFirst("meta-transid");

					Map<String, String> responseHeaders = new HashMap<>();
					if (meta_senderapp != null && meta_transid != null) {
						responseHeaders.put("meta-senderapp", meta_senderapp);
						responseHeaders.put("meta-transid", meta_transid);
					}
					if (tenantKey != null) {
						context.put(AppConstant.VENDOR_ID, tenantKey);
						LOGGER.info("VENDOR_ID added : " + AppConstant.VENDOR_ID + "," + tenantKey);
					}

					ContextSnapshotFactory.builder().build().setThreadLocalsFrom(context,
							ObservationThreadLocalAccessor.KEY);
					return context;
				}).doFinally(signalType -> {
					long endTime = System.currentTimeMillis();
					long executionTime = endTime - startTime;
					
					//exchange.getMultipartData()
//                    MovieContext obj = (MovieContext)dgsContext.getCustomContext();
//            		Map<String,Object> data = executionResult.getData();
//            		
//            		
//            		if(obj.isMetadataRequested() && obj.getMetaData()!=null) {
//            			
//            			if(data !=null && data.get("metaData") ==null) {
//            				
//            				//LinkedHashMap<String, Object> map = (LinkedHashMap<String,Object>)data.get("data");
//            				//if(map.get("metaData")==null)
//            					data.put("metaData",obj.getMetaData() );
//            			}
//            				
//            		}
					

					/**
					 * Extracting traceId added in TracerConfiguration Webfilter bean
					 */
					List<String> traceIds = ofNullable(exchange.getResponse().getHeaders().get("traceId"))
							.orElse(List.of());
					MetricsLogTemplate metricsLogTemplate = new MetricsLogTemplate(String.join(",", traceIds),
							exchange.getLogPrefix().trim(), executionTime);
					try {
						log.info(new ObjectMapper().writeValueAsString(metricsLogTemplate));
					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}
				});
	}

	record MetricsLogTemplate(String traceId, String logPrefix, long executionTime) {
	}
}
