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

    /// ---- INTEGRATION TESTS ---- ///

    @Test
    public void createQuestionTest() {
        // prepare test data
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

        // execute test
        QuestionWrapperDTO wrapper = this.questionCreator.createQuestions(mockAggregatedQuestions);

        // verify results
        assertThat(wrapper).isNotNull();
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

    /// ---- UNIT TESTS ---- ///

    @Test
    public void buildSentenceTest() {
        // execute test
        String sentence = questionCreator.buildSentence(MOCK_QUESTION_LINES);

        // verify result
        assertThat(sentence).isNotNull().isEqualTo("Question 1?");
    }

    @Test
    public void buildAnswersTest() {
        // execute test
        List<AnswerDTO> answers = this.questionCreator.buildAnswers(MOCK_QUESTION_LINES);

        // verify results
        assertThat(answers).isNotNull().hasSize(2);
        assertThat(answers.get(0).getCorrect()).isEqualTo(true);
        assertThat(answers.get(0).getSentence()).isEqualTo("Answers 1_1");
        assertThat(answers.get(1).getCorrect()).isEqualTo(false);
        assertThat(answers.get(1).getSentence()).isEqualTo("Answers 1_2");
    }

    @Test
    public void addAdditionalAnswersTest() {
        // execute test
        List<AnswerDTO> answers = this.questionCreator.addAdditionalAnswer(MOCK_ANSWERS);

        // verify results
        assertThat(answers).isNotNull().hasSize(3);
        assertThat(answers.get(2).getSentence()).isEqualTo("Don't know");
        assertThat(answers.get(2).getCorrect()).isEqualTo(false);
    }

    @Test
    public void buildQuestionTest() {
        // prepare mock data
        String questionSentence = "Question 1?";

        // execute test
        QuestionDTO question = this.questionCreator.buildQuestion(questionSentence, MOCK_ANSWERS);

        // verify results
        assertThat(question).isNotNull();
        assertThat(question.getSentence()).isEqualTo(questionSentence);
        assertThat(question.getAnswers()).isEqualTo(MOCK_ANSWERS);
    }

    @Test
    public void buildWrapperTest() {
        // prepare mock data
        List<QuestionDTO> questions = List.of(Mockito.mock(QuestionDTO.class), Mockito.mock(QuestionDTO.class));

        // execute test
        QuestionWrapperDTO wrapper = this.questionCreator.buildWrapper(questions);

        // verify results
        assertThat(wrapper).isNotNull();
        assertThat(wrapper.getQuestions()).isEqualTo(questions);
    }
}
