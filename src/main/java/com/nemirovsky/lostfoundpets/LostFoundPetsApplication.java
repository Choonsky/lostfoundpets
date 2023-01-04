package com.nemirovsky.lostfoundpets;

import com.nemirovsky.lostfoundpets.model.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class LostFoundPetsApplication implements CommandLineRunner {

    private ReactiveMongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(LostFoundPetsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        setup();
    }

    private void setup() {
        Pet p1 = new Pet();
        mongoTemplate.save(p1);
    }
}
