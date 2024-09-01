package com.artem.task.dto;

public class MovieCastDTO {
    private String characterName;

    public MovieCastDTO() {}

    public MovieCastDTO(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
