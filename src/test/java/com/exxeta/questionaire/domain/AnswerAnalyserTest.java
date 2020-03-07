package com.exxeta.questionaire.domain;

import com.exxeta.questionaire.model.AnswerDTO;
import com.exxeta.questionaire.model.QuestionDTO;
import com.exxeta.questionaire.model.QuestionResultDTO;
import com.exxeta.questionaire.model.ResultDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AnswerAnalyserTest {

    private AnswerAnalyser answerAnalyser;

    private QuestionDTO question1;
    private QuestionDTO question2;
    private QuestionDTO question3;

    private final AnswerDTO ANSWER_1 = new AnswerDTO("Answer_1",false);
    private final AnswerDTO ANSWER_2 = new AnswerDTO("Answer_2",false);
    private final AnswerDTO ANSWER_3 = new AnswerDTO("Answer_3",true);
    private final AnswerDTO ANSWER_4 = new AnswerDTO("Don't Know",false);

    @Before
    public void before() {
        this.answerAnalyser = new AnswerAnalyser();
        this.question1 = new QuestionDTO(
                "Question 1",
                List.of(ANSWER_1,ANSWER_2,ANSWER_3,ANSWER_4),
                0l
        );

        this.question2 = new QuestionDTO(
                "Question 2",
                List.of(ANSWER_2,ANSWER_3,ANSWER_1,ANSWER_4),
                0l
        );

        this.question3 = new QuestionDTO(
                "Question 3",
                List.of(ANSWER_3,ANSWER_1,ANSWER_2,ANSWER_4),
                0l
        );
    }

    @Test
    public void analyseTest() {
        ResultDTO resultDTO = answerAnalyser.analyse(List.of(question1, question2, question3));

        assertThat(resultDTO.getTotalCorrect()).isEqualTo(1);
        assertThat(resultDTO.getTotalQuestions()).isEqualTo(3);
        assertThat(resultDTO.getQuestionResults().get(0).getCorrect()).isFalse();
        assertThat(resultDTO.getQuestionResults().get(1).getCorrect()).isFalse();
        assertThat(resultDTO.getQuestionResults().get(2).getCorrect()).isTrue();
    }

    @Test
    public void analyseAnsweredQuestionCorrectTest() {
        QuestionResultDTO questionResultDTO = answerAnalyser.analyseAnsweredQuestion(question3);

        assertThat(questionResultDTO).isNotNull();
        assertThat(questionResultDTO.getCorrect()).isTrue();
    }

    @Test
    public void analyseAnsweredQuestionIncorrectTest() {
        QuestionResultDTO questionResultDTO = answerAnalyser.analyseAnsweredQuestion(question1);

        assertThat(questionResultDTO).isNotNull();
        assertThat(questionResultDTO.getCorrect()).isFalse();
    }
}
