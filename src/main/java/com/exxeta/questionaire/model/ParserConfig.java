package com.exxeta.questionaire.model;

public enum ParserConfig {
    QUESTION_MARKER("?"),
    CORRECT_ANSWER_MARKER("*");

    private String character;

    ParserConfig(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
