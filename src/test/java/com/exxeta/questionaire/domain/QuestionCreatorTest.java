package com.exxeta.questionaire.domain;

import com.exxeta.questionaire.model.AnswerDTO;
import com.exxeta.questionaire.model.QuestionDTO;
import com.exxeta.questionaire.model.QuestionWrapperDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class QuestionCreatorTest {

    private QuestionCreator questionCreator;

    private final List<String> MOCK_QUESTION_LINES = List.of(
            "?Question 1",
            "*Answers 1_1",
            "Answers 1_2"
    );

    private final List<AnswerDTO> MOCK_ANSWERS = new ArrayList<>(Arrays.asList(
            AnswerDTO.builder()
                    .sentence("Answer 1")
                    .correct(false)
                    .build(),

            AnswerDTO.builder()
                    .sentence("Answer 2")
                    .correct(true)
                    .build()
    ));

    @Before
    public void before() {
        this.questionCreator = new QuestionCreator();
    }


    @Test
    public void createQuestionTest() {
        List<List<String>> mockAggregatedQuestions = List.of(
                List.of(
                        "?Question 1",
                        "*Answer 1_1",
                        "Answer 1_2"
                ),
                List.of(
                        "?Question 2",
                        "Answer 2_1",
                        "*Answer 2_2",
                        "Answer 2_3"
                )
        );

        QuestionWrapperDTO wrapper = this.questionCreator.createQuestions(mockAggregatedQuestions);

        assertThat(wrapper.getQuestions()).hasSize(2);

        assertThat(wrapper.getQuestions().get(0).getSentence()).isEqualTo("Question 1?");
        assertThat(wrapper.getQuestions().get(0).getAnswers().size()).isEqualTo(3);
        assertThat(wrapper.getQuestions().get(0).getAnswers().get(0).getCorrect()).isEqualTo(true);
        assertThat(wrapper.getQuestions().get(0).getAnswers().get(0).getSentence()).isEqualTo("Answer 1_1");
        assertThat(wrapper.getQuestions().get(0).getAnswers().get(1).getCorrect()).isEqualTo(false);
        assertThat(wrapper.getQuestions().get(0).getAnswers().get(1).getSentence()).isEqualTo("Answer 1_2");
        assertThat(wrapper.getQuestions().get(0).getAnswers().get(2).getCorrect()).isEqualTo(false);
        assertThat(wrapper.getQuestions().get(0).getAnswers().get(2).getSentence()).isEqualTo("Don't know");

        assertThat(wrapper.getQuestions().get(1).getSentence()).isEqualTo("Question 2?");
        assertThat(wrapper.getQuestions().get(1).getAnswers().size()).isEqualTo(4);
        assertThat(wrapper.getQuestions().get(1).getAnswers().get(0).getCorrect()).isEqualTo(false);
        assertThat(wrapper.getQuestions().get(1).getAnswers().get(0).getSentence()).isEqualTo("Answer 2_1");
        assertThat(wrapper.getQuestions().get(1).getAnswers().get(1).getCorrect()).isEqualTo(true);
        assertThat(wrapper.getQuestions().get(1).getAnswers().get(1).getSentence()).isEqualTo("Answer 2_2");
        assertThat(wrapper.getQuestions().get(1).getAnswers().get(2).getCorrect()).isEqualTo(false);
        assertThat(wrapper.getQuestions().get(1).getAnswers().get(2).getSentence()).isEqualTo("Answer 2_3");
        assertThat(wrapper.getQuestions().get(1).getAnswers().get(3).getCorrect()).isEqualTo(false);
        assertThat(wrapper.getQuestions().get(1).getAnswers().get(3).getSentence()).isEqualTo("Don't know");
    }

    @Test
    public void buildSentenceTest() {
        String sentence = questionCreator.buildSentence(MOCK_QUESTION_LINES);

        assertThat(sentence).isEqualTo("Question 1?");
    }

    @Test
    public void buildAnswersTest() {
        List<AnswerDTO> answers = this.questionCreator.buildAnswers(MOCK_QUESTION_LINES);

        assertThat(answers).hasSize(2);
        assertThat(answers.get(0).getCorrect()).isEqualTo(true);
        assertThat(answers.get(0).getSentence()).isEqualTo("Answers 1_1");
        assertThat(answers.get(1).getCorrect()).isEqualTo(false);
        assertThat(answers.get(1).getSentence()).isEqualTo("Answers 1_2");
    }

    @Test
    public void addAdditionalAnswersTest() {
        List<AnswerDTO> answers = this.questionCreator.addAdditionalAnswer(MOCK_ANSWERS);

        assertThat(answers).hasSize(3);
        assertThat(answers.get(2).getSentence()).isEqualTo("Don't know");
        assertThat(answers.get(2).getCorrect()).isEqualTo(false);
    }

    @Test
    public void buildQuestionTest() {
        String questionSentence = "Question 1?";

        QuestionDTO question = this.questionCreator.buildQuestion(questionSentence, MOCK_ANSWERS);

        assertThat(question);
        assertThat(question.getSentence()).isEqualTo(questionSentence);
        assertThat(question.getAnswers()).isEqualTo(MOCK_ANSWERS);
    }

    @Test
    public void buildWrapperTest() {
        List<QuestionDTO> questions = List.of(Mockito.mock(QuestionDTO.class), Mockito.mock(QuestionDTO.class));

        QuestionWrapperDTO wrapper = this.questionCreator.buildWrapper(questions);

        assertThat(wrapper);
        assertThat(wrapper.getQuestions()).isEqualTo(questions);
    }
}
