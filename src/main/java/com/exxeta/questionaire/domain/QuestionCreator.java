package com.exxeta.questionaire.domain;

import com.exxeta.questionaire.model.AnswerDTO;
import com.exxeta.questionaire.model.ParserConfig;
import com.exxeta.questionaire.model.QuestionDTO;
import com.exxeta.questionaire.model.QuestionWrapperDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionCreator {

    public QuestionWrapperDTO createQuestions(List<List<String>> aggregatedQuestions) {
        List<QuestionDTO> questions = new ArrayList<>();
        aggregatedQuestions.forEach(aggregatedQuestion -> {
            QuestionDTO question = createQuestion(aggregatedQuestion);
            questions.add(question);
        });
        QuestionWrapperDTO wrapper = buildWrapper(questions);
        return wrapper;
    }

    QuestionDTO createQuestion(List<String> aggregatedQuestion) {
        String sentence = buildSentence(aggregatedQuestion);
        List<AnswerDTO> answers = buildAnswers(aggregatedQuestion);
        List<AnswerDTO> answersWithAdditional = addAdditionalAnswer(answers);
        return buildQuestion(sentence, answersWithAdditional);
    }

    String buildSentence(List<String> rawQuestion) {
        String sentence = rawQuestion.get(0);
        sentence = sentence.replace(ParserConfig.QUESTION_MARKER.getCharacter(), "");
        sentence += "?";
        return sentence;
    }

    List<AnswerDTO> buildAnswers(List<String> rawQuestion) {
        List<AnswerDTO> answers = new ArrayList<>();
        for (int i = 1; i < rawQuestion.size(); i++) {
            boolean correct = false;
            String answer = rawQuestion.get(i);
            if (answer.startsWith(ParserConfig.CORRECT_ANSWER_MARKER.getCharacter())) {
                correct = true;
                answer = answer.replace(ParserConfig.CORRECT_ANSWER_MARKER.getCharacter(), "");
            }
            AnswerDTO answerDTO = AnswerDTO.builder()
                    .sentence(answer)
                    .correct(correct)
                    .build();

            answers.add(answerDTO);
        }
        return answers;
    }

    List<AnswerDTO> addAdditionalAnswer(List<AnswerDTO> answers) {
        AnswerDTO additionalAnswer = AnswerDTO.builder()
                                        .correct(false)
                                        .sentence("Don't know")
                                        .build();
        answers.add(additionalAnswer);
        return answers;
    }

    QuestionDTO buildQuestion(String sentence, List<AnswerDTO> answers) {
        return QuestionDTO.builder()
                .sentence(sentence)
                .answers(answers)
                .build();
    }

    QuestionWrapperDTO buildWrapper(List<QuestionDTO> questions) {
        return QuestionWrapperDTO.builder().questions(questions).build();
    }
}
