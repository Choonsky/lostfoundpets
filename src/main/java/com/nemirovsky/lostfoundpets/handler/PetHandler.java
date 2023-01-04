package com.nemirovsky.lostfoundpets.handler;

import com.nemirovsky.lostfoundpets.model.Pet;
import com.nemirovsky.lostfoundpets.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PetHandler {

    private final PetService petService;

    public Mono<ServerResponse> getAllPets(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(petService.getAllPets(), Pet.class);
    }

    public Mono<ServerResponse> getPetById(ServerRequest request) {
        return petService
                .findById(request.pathVariable("petId"))
                .flatMap(pet -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(pet, Pet.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Pet> pet = request.bodyToMono(Pet.class);

        return pet
                .flatMap(p -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(petService.createPet(p), Pet.class)
                );
    }

    public Mono<ServerResponse> updatePetById(ServerRequest request) {
        String id = request.pathVariable("petId");
        Mono<Pet> updatedPet = request.bodyToMono(Pet.class);

        return updatedPet
                .flatMap(p -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(petService.updatePet(id, p), Pet.class)
                );
    }

    public Mono<ServerResponse> deletePetById(ServerRequest request){
        return petService.deletePet(request.pathVariable("petId"))
                .flatMap(p -> ServerResponse.ok().body(p, Pet.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
