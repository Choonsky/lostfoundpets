package com.nemirovsky.lostfoundpets.service;

import com.nemirovsky.lostfoundpets.model.Pet;
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
        Pet p1 = new Pet();
        Pet p2 = new Pet();
        Pet p3 = new Pet();
        Pet p4 = new Pet();
        Pet p5 = new Pet();
        p1.setId(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME));
        p2.setId(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME));
        p3.setId(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME));
        p4.setId(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME));
        p5.setId(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME));
        p1.setName("Alpha");
        p2.setName("Bravo");
        p3.setName("Charlie");
        p4.setName("Doodle");
        p5.setName("Eugeniya");
        pets.deleteAll()
                .thenMany(Flux.just(p1, p2, p3, p4, p5)
                        .flatMap(this.pets::save))
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done DB initialization...")
                );
    }

}
