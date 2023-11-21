package com.twc.movie.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.twc.movie.config.AppConstant;

import reactor.core.publisher.Mono;

//@Component
public class TenantFilter implements WebFilter {

	
   @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
       return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(AppConstant.VENDOR_HEADER_KEY))
               .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)))
               .flatMap(tenantKey -> chain.filter(exchange).contextWrite(ctx -> ctx.put(AppConstant.VENDOR_ID, tenantKey)));

    }
}