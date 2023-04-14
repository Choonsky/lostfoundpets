package com.nemirovsky.lostfoundpets.config;

import com.nemirovsky.lostfoundpets.handler.PetHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> routes(PetHandler handler) {
        return route(GET("/pets").and(accept(MediaType.APPLICATION_JSON)), handler::getAllPets)
                .andRoute(GET("/").and(accept(MediaType.TEXT_HTML)), handler::mainPage)
                .andRoute(GET("/pets/{petId}").and(contentType(MediaType.APPLICATION_JSON)), handler::getPetById)
                .andRoute(POST("/pets").and(accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(PUT("/pets/{petId}").and(contentType(MediaType.APPLICATION_JSON)), handler::updatePetById)
                .andRoute(DELETE("/pets/{userId}").and(accept(MediaType.APPLICATION_JSON)), handler::deletePetById)
                .andRoute(GET("/telebot-test").and(contentType(MediaType.APPLICATION_JSON)), handler::printTelebotInfo)
                .andRoute(POST("/telebot-test").and(accept(MediaType.APPLICATION_JSON)), handler::acceptTelebotInfo);
    }
    @Bean
    public RouterFunction<ServerResponse> staticResourceRouter() {
        return RouterFunctions.resources("/**", new ClassPathResource("static/"));
    }
}
