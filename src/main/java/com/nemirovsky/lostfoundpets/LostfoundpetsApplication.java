package com.nemirovsky.lostfoundpets;

import com.nemirovsky.lostfoundpets.model.Pet;
import com.nemirovsky.lostfoundpets.repository.PetRepository;
import com.nemirovsky.lostfoundpets.service.DbInitService;
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

    private final DbInitService dbInitService;

    public LostfoundpetsApplication(DbInitService dbInitService) {
        this.dbInitService = dbInitService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LostfoundpetsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        dbInitService.setup();
    }

}
