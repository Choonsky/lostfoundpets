package com.nemirovsky.lostfoundpets.service;

import com.nemirovsky.lostfoundpets.model.Pet;
import com.nemirovsky.lostfoundpets.model.PetSpecies;
import com.nemirovsky.lostfoundpets.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
        Pet p1 = new Pet.PetBuilder()
                .id(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME))
                .name("Alpha")
                .breed("Husky")
                .species((PetSpecies.DOG))
                .addedByUser(null)
                .build();
        Pet p2 = new Pet.PetBuilder()
                .id(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME))
                .name("Bravo")
                .breed("Persian")
                .species((PetSpecies.CAT))
                .addedByUser(null)
                .build();
        Pet p3 = new Pet.PetBuilder()
                .id(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME))
                .name("Charlie")
                .breed(null)
                .species((PetSpecies.RABBIT))
                .addedByUser(null)
                .build();
        Pet p4 = new Pet.PetBuilder()
                .id(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME))
                .name("Doodle")
                .breed("Simple hamster")
                .species((PetSpecies.HAMSTER))
                .addedByUser(null)
                .build();
        Pet p5 = new Pet.PetBuilder()
                .id(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME))
                .name("Eugeniya")
                .breed("Golden fish")
                .species((PetSpecies.OTHER))
                .addedByUser(null)
                .build();
        pets.deleteAll()
                .thenMany(Flux.just(p1, p2, p3, p4, p5)
                        .flatMap(this.pets::save))
                .log()
                .subscribe(null, null, () -> log.info("done DB initialization..."));
    }

}
