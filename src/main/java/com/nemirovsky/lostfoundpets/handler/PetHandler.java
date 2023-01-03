package com.nemirovsky.lostfoundpets.handler;

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

    public Mono<ServerResponse> getAllDocuments(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(petService.getAllDocuments(), OcrDocument.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        return petService
                .findById(request.pathVariable("docId"))
                .flatMap(user -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(user, OcrDocument.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<OcrDocument> doc = request.bodyToMono(OcrDocument.class);

        return doc
                .flatMap(d -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(petService.createDocument(d), OcrDocument.class)
                );
    }

    public Mono<ServerResponse> updateUserById(ServerRequest request) {
        String id = request.pathVariable("docId");
        Mono<OcrDocument> updatedDoc = request.bodyToMono(OcrDocument.class);

        return updatedDoc
                .flatMap(d -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(petService.updateDocument(id, d), OcrDocument.class)
                );
    }

    public Mono<ServerResponse> deleteUserById(ServerRequest request){
        return petService.deleteDocument(request.pathVariable("docId"))
                .flatMap(d -> ServerResponse.ok().body(d, OcrDocument.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
