package com.exxeta.questionaire.domain;

import com.exxeta.questionaire.model.AnswerDTO;
import com.exxeta.questionaire.model.QuestionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionCreator {

    private final String QUESTION_MARKER = "?";

    private final String CORRECT_ANSWER_MARKER = "*";


    public List<QuestionDTO> createQuestions(List<String> lines) {
        List<Integer> indices = getIndicesOfQuestions(lines);
        List<List<String>> aggregatedQuestions = aggregateQuestions(indices, lines);
        List<QuestionDTO> questions = buildAllQuestions(aggregatedQuestions);
        return questions;
    }

    List<Integer> getIndicesOfQuestions(List<String> lines) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(QUESTION_MARKER)) {
                indices.add(i);
            }
        }
        return indices;
    }

    List<List<String>> aggregateQuestions(List<Integer> questionIndices, List<String> lines) {
        List<List<String>> aggregated = new ArrayList<>();
        int start;
        int end;
        for (int i = 0; i < questionIndices.size(); i++) {
            if (i + 1 < questionIndices.size()) {
                end = questionIndices.get(i + 1);
            } else {
                /* size() is fine since subList's toIndex is exclusive */
                end = lines.size();
            }
            start = questionIndices.get(i);

            aggregated.add(lines.subList(start, end));
        }
        return aggregated;
    }

    List<QuestionDTO> buildAllQuestions(List<List<String>> rawQuestions) {
        return rawQuestions.stream().map(rawQuestion -> buildSingleQuestion(rawQuestion)).collect(Collectors.toList());
    }

    QuestionDTO buildSingleQuestion(List<String> rawQuestion) {
        String sentence = rawQuestion.get(0);
        sentence = sentence.replace(QUESTION_MARKER, "");
        sentence += "?";
        List<AnswerDTO> answers = new ArrayList<>();
        for (int i = 1; i < rawQuestion.size(); i++) {
            boolean correct = false;
            String answer = rawQuestion.get(i);
            if (answer.startsWith(CORRECT_ANSWER_MARKER)) {
                correct = true;
                answer = answer.replace(CORRECT_ANSWER_MARKER, "");
            }
            AnswerDTO answerDTO = AnswerDTO.builder()
                    .sentence(answer)
                    .selected(false)
                    .correct(correct)
                    .build();

            answers.add(answerDTO);
        }
        return QuestionDTO.builder()
                .sentence(sentence)
                .answers(answers)
                .build();
    }
}
