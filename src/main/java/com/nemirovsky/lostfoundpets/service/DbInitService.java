package com.nemirovsky.lostfoundpets.service;

import com.nemirovsky.lostfoundpets.model.Location;
import com.nemirovsky.lostfoundpets.model.Pet;
import com.nemirovsky.lostfoundpets.model.PetSpecies;
import com.nemirovsky.lostfoundpets.model.PetStatus;
import com.nemirovsky.lostfoundpets.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class DbInitService {

    private final PetRepository pets;

    private final SequenceGeneratorService sequenceGeneratorService;

    public DbInitService(SequenceGeneratorService sequenceGeneratorService, PetRepository pets) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.pets = pets;
    }

    public void setup() {
        log.info("Start DB initialization...");
        String nextId = sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME);
        Pet p1 = new Pet.PetBuilder()
                .id(nextId)
                .name("Alpha")
                .breed("Husky")
                .species((PetSpecies.DOG))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .petStatus(PetStatus.FOUND)
                .build();
        nextId = sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME);
        Pet p2 = new Pet.PetBuilder()
                .id(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME))
                .name("Bravo")
                .breed("Persian")
                .species((PetSpecies.CAT))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .petStatus(PetStatus.FOUND)
                .build();
        nextId = sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME);
        Pet p3 = new Pet.PetBuilder()
                .id(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME))
                .name("Charlie")
                .breed(null)
                .species((PetSpecies.RABBIT))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .petStatus(PetStatus.LOST)
                .build();
        nextId = sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME);
        Pet p4 = new Pet.PetBuilder()
                .id(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME))
                .name("Doodle")
                .breed("Simple hamster")
                .species((PetSpecies.HAMSTER))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .petStatus(PetStatus.LOST)
                .build();
        nextId = sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME);
        Pet p5 = new Pet.PetBuilder()
                .id(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME))
                .name("Eugeniya")
                .breed("Golden fish")
                .species((PetSpecies.OTHER))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .petStatus(PetStatus.NEITHER)
                .build();
        pets.deleteAll()
                .thenMany(Flux.just(p1, p2, p3, p4, p5)
                        .flatMap(this.pets::save))
                .log()
                .subscribe(null, null, () -> log.info("done DB initialization..."));
    }

}
