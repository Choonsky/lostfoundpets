package com.nemirovsky.lostfoundpets;

import com.nemirovsky.lostfoundpets.model.Pet;
import com.nemirovsky.lostfoundpets.repository.PetRepository;
import com.nemirovsky.lostfoundpets.service.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
public class LostfoundpetsApplication implements CommandLineRunner {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final PetRepository pets;

    private final SequenceGeneratorService sequenceGeneratorService;

    public LostfoundpetsApplication(ReactiveMongoTemplate reactiveMongoTemplate,
                                    SequenceGeneratorService sequenceGeneratorService, PetRepository pets) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.pets = pets;
    }

    public static void main(String[] args) {
        SpringApplication.run(LostfoundpetsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        setup();
    }

    private void setup() {
        log.info("Start initialization...");
        Pet p1 = new Pet();
        Pet p2 = new Pet();
        Pet p3 = new Pet();
        Pet p4 = new Pet();
        Pet p5 = new Pet();
        p1.setId(sequenceGeneratorService.generateSequence(Pet.SEQUENCE_NAME));
        p1.setName("Charlie");
        pets.deleteAll()
                .thenMany(Flux.just(p1, p2, p3, p4, p5)
                        .flatMap(this.pets::save))
                .log()
                .subscribe(
                null,
                null,
                () -> log.info("done initialization...")
        );
    }
}
