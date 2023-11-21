package com.twc.movie.config;

import com.twc.generated.types.MetaData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MovieContext {
	
	private final String tenantId = "tenantId";
	private MetaData metaData;
	private boolean isError;
	private boolean metadataRequested;
	
	
}
