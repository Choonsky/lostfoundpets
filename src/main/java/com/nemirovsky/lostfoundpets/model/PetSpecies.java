package com.nemirovsky.lostfoundpets.model;

import lombok.Getter;

@Getter
public enum PetSpecies {

    CAT("Cat"),
    DOG ("Dog"),
    RABBIT ("Rabbit or hare"),
    HAMPSTER ("Hampster or something like"),
    TURTLE ("Turtle of a kind"),
    OTHER ("Other animal");
    private final String name;

    PetSpecies(String name) {
        this.name = name;
    }
}
