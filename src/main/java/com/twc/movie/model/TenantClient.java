package com.twc.movie.model;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.twc.movie.service.MovieService;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <h2>TenantClient</h2>
 *
 * <p>
 * Description: this class is model for tenant datasource.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantClient extends TenantDatasource {

	private static final Log LOGGER = LogFactory.getLog(TenantClient.class);
	
    private MongoClient client;

    public TenantClient(@NotNull @NotBlank String tenantId, @NotNull @NotBlank String databaseName, @NotNull int port, @NotNull @NotBlank String host, @NotNull @NotBlank String username, @NotNull @NotBlank String password) {
        super(tenantId, databaseName, port, host, username, password);
        createClient();
    }

    /**
     * Init mongo client for tenant
     */
    private void createClient() {
        MongoCredential credential = MongoCredential.createCredential(this.getUsername(), this.getDatabase(), this.getPassword().toCharArray());
        //MongoCredential credential = MongoCredential.createCredential(this.getUsername(), this.getDatabase(), this.getPassword().toCharArray());
        LOGGER.info("Creating connection for database : " + this.getDatabase());
        client = MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(Collections.singletonList(new ServerAddress(this.getHost(), this.getPort()))))
                //.credential(credential)
                .build());
    }
}
