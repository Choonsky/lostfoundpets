package com.nemirovsky.lostfoundpets.config;

import com.nemirovsky.lostfoundpets.handler.PetHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> routes(PetHandler handler) {
        return route(GET("/pets").and(accept(MediaType.APPLICATION_JSON)), handler::getAllPets)
                .andRoute(GET("/pets/{petId}").and(contentType(MediaType.APPLICATION_JSON)), handler::getPetById)
                .andRoute(POST("/pets").and(accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(PUT("/pets/{petId}").and(contentType(MediaType.APPLICATION_JSON)), handler::updatePetById)
                .andRoute(DELETE("/pets/{userId}").and(accept(MediaType.APPLICATION_JSON)), handler::deletePetById);
    }
}
