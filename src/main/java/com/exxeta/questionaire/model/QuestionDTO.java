package com.exxeta.questionaire.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private String sentence;

    private List<AnswerDTO> answers;

    private Long selectedAnswer;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    public Long getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(Long selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
