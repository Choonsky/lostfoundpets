package com.nemirovsky.lostfoundpets.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@ToString
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "pets")
public class Pet {
    @Id
    private String id;
    private User addedByUser;
    private User ownedByUser;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String name;
    private PetSpecies species;
    private String breed;
    private LocalDate birthDate;
    private Location location;
    private String[] imgSources;
    private int mainImageIndex = 0;
}