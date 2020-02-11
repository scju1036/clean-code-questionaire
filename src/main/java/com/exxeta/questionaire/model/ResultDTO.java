package com.exxeta.questionaire.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
public class ResultDTO {

    private Integer totalQuestions;
    private Integer totalCorrect;
    private List<QuestionResultDTO> questionResults;

    public ResultDTO(){
        questionResults = new ArrayList<>();
        totalCorrect = 0;
        totalQuestions = 0;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(Integer totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public List<QuestionResultDTO> getQuestionResults() {
        return questionResults;
    }

    public void setQuestionResults(List<QuestionResultDTO> questionResults) {
        this.questionResults = questionResults;
    }
}
