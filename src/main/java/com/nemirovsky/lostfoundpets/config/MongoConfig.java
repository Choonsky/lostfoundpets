package com.nemirovsky.lostfoundpets.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class MongoConfig {
    @Bean
    public MongoClient mongo() {

        String truststore = "/tmp/certs/rds-truststore.jks";
        String truststorePassword = "KrolikEst_1";

        System.setProperty("javax.net.ssl.trustStore", truststore);
        System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);

        ConnectionString connectionString = new ConnectionString("mongodb://DocumentDBAdmin:KrolikEst_1@lostfountpets" +
                "-docdb-tls.cluster-cuk3aks6r1zn.ap-northeast-1.docdb.amazonaws.com:27017/?ssl=true&replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongo(), "lostfoundpets");
    }
}
