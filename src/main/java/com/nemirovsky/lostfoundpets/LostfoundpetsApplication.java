package com.nemirovsky.lostfoundpets;

import com.nemirovsky.lostfoundpets.service.DbInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
