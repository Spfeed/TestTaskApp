package com.artem.task.dto;

public class ActorForMovieDTO {
    private String name;
    private String lastName;
    private String characterName;

    public ActorForMovieDTO() {}

    public ActorForMovieDTO(String name, String lastName, String characterName) {
        this.name = name;
        this.lastName = lastName;
        this.characterName = characterName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
