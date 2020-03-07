package com.exxeta.questionaire.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class QuestionParserTest {

    private QuestionParser questionParser;

    private final List<String> MOCK_LINES = List.of(
            "?Question 1",
            "Answers 1_1",
            "Answers 1_2",
            "?Question 2",
            "Answers 2_1",
            "Answers 2_2"
    );

    @Before
    public void before() {
        this.questionParser = new QuestionParser();
    }

    /// ---- INTEGRATION TESTS ---- ///

    @Test
    public void parseTest() {
        List<List<String>> aggregated = questionParser.parse(MOCK_LINES);

        assertThat(aggregated).hasSize(2);
        assertThat(aggregated.get(0)).isNotNull().hasSize(3);
        assertThat(aggregated.get(0).get(0)).isEqualTo("?Question 1");
        assertThat(aggregated.get(0).get(1)).isEqualTo("Answers 1_1");
        assertThat(aggregated.get(0).get(2)).isEqualTo("Answers 1_2");
        assertThat(aggregated.get(1)).isNotNull().hasSize(3);
        assertThat(aggregated.get(1).get(0)).isEqualTo("?Question 2");
        assertThat(aggregated.get(1).get(1)).isEqualTo("Answers 2_1");
        assertThat(aggregated.get(1).get(2)).isEqualTo("Answers 2_2");
    }

    /// ---- UNIT TESTS ---- ///

    @Test
    public void getIndicesOfQuestionsTestWithValidData() {
        List<Integer> indices = questionParser.getIndicesOfQuestions(MOCK_LINES);

        assertThat(indices).hasSize(2);
        assertThat(indices.get(0)).isEqualTo(0);
        assertThat(indices.get(1)).isEqualTo(3);
    }

    @Test
    public void getIndicesOfQuestionsTestWithEmptyData() {
        List<String> mockLines = new ArrayList<>();

        List<Integer> indices = questionParser.getIndicesOfQuestions(mockLines);

        assertThat(indices).hasSize(0);
    }

    @Test
    public void aggregateQuestionsTestWithValidData() {
        List<Integer> mockIndices = List.of(0,3);

        List<List<String>> aggregated = questionParser.aggregateQuestions(mockIndices, MOCK_LINES);

        assertThat(aggregated).hasSize(2);
        assertThat(aggregated.get(0)).isNotNull().hasSize(3);
        assertThat(aggregated.get(0).get(0)).isEqualTo("?Question 1");
        assertThat(aggregated.get(0).get(1)).isEqualTo("Answers 1_1");
        assertThat(aggregated.get(0).get(2)).isEqualTo("Answers 1_2");
        assertThat(aggregated.get(1)).isNotNull().hasSize(3);
        assertThat(aggregated.get(1).get(0)).isEqualTo("?Question 2");
        assertThat(aggregated.get(1).get(1)).isEqualTo("Answers 2_1");
        assertThat(aggregated.get(1).get(2)).isEqualTo("Answers 2_2");
    }

    @Test
    public void aggregateQuestionsTestWithEmptyData() {
        List<String> mockLines = new ArrayList<>();
        List<Integer> mockIndices = new ArrayList<>();

        List<List<String>> aggregated = questionParser.aggregateQuestions(mockIndices, mockLines);

        assertThat(aggregated).hasSize(0);
    }
}
