package com.exxeta.questionaire.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class QuestionCreatorTest {

    private QuestionCreator questionCreator;

    private final List<String> MOCK_QUESTION_LINES = List.of(
            "?Question 1",
            "Answers 1_1",
            "Answers 1_2"
    );

    @Before
    public void before() {
        this.questionCreator = new QuestionCreator();
    }

    @Test
    public void buildSentenceTest() {
        // execute test
        String sentence = questionCreator.buildSentence(MOCK_QUESTION_LINES);

        // verify result
        assertThat(sentence).isNotNull().isEqualTo("Question 1?");
    }
}
