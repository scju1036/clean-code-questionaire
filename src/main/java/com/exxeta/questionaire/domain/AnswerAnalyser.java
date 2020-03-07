package com.exxeta.questionaire.domain;

import com.exxeta.questionaire.model.AnswerDTO;
import com.exxeta.questionaire.model.QuestionDTO;
import com.exxeta.questionaire.model.QuestionResultDTO;
import com.exxeta.questionaire.model.ResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerAnalyser {

    public ResultDTO analyse(List<QuestionDTO> questions) {
        ResultDTO resultDTO = new ResultDTO();

        for (QuestionDTO question : questions) {
            QuestionResultDTO questionResultDTO = analyseAnsweredQuestion(question);
            resultDTO.getQuestionResults().add(questionResultDTO);
        }

        // Set the total number of questions
        resultDTO.setTotalQuestions(questions.size());

        Integer totalCorrect = numberOfCorrectAnswers(resultDTO);
        resultDTO.setTotalCorrect(totalCorrect);

        return resultDTO;
    }



    QuestionResultDTO analyseAnsweredQuestion(QuestionDTO question){
        QuestionResultDTO questionResultDTO = new QuestionResultDTO();

        String originalQuestionSentence = extractQuestionSentence(question);
        questionResultDTO.setSentence(originalQuestionSentence);

        String correctAnswerSentence = extractCorrectAnswerSentence(question);
        questionResultDTO.setCorrectAnswer(correctAnswerSentence);

        String selectedAnswerSentence = extractSelectedAnswerSentence(question);
        questionResultDTO.setSelectedAnswer(selectedAnswerSentence);

        boolean isSelectedAnswerCorrect = isSelectedAnswerCorrect(question);
        questionResultDTO.setCorrect(isSelectedAnswerCorrect);

        return questionResultDTO;
    }

    String extractQuestionSentence(QuestionDTO questionDTO) {
        return questionDTO.getSentence();
    }

    String extractCorrectAnswerSentence(QuestionDTO questionDTO){
        // Get all answers
        List<AnswerDTO> answers = questionDTO.getAnswers();

        // Get the correct answer
        Optional<AnswerDTO> correctAnswerOptional = answers.stream()
                .filter(answerDTO -> answerDTO.getCorrect())
                .findFirst();

        return correctAnswerOptional.get().getSentence();
    }

    String extractSelectedAnswerSentence(QuestionDTO questionDTO){
        if (questionDTO.getSelectedAnswer() == null) {
            return "";
        }

        // Otherwise, pull the answer and check if it was the correct one.
        Integer selectedAnswerIndex = Math.toIntExact(questionDTO.getSelectedAnswer());
        AnswerDTO selectedAnswer = questionDTO.getAnswers().get(selectedAnswerIndex);

        return selectedAnswer.getSentence();
    }

    boolean isSelectedAnswerCorrect(QuestionDTO questionDTO){
        if (questionDTO.getSelectedAnswer() == null) {
            return false;
        }

        // Otherwise, pull the answer and check if it was the correct one.
        Integer selectedAnswerIndex = Math.toIntExact(questionDTO.getSelectedAnswer());
        AnswerDTO selectedAnswer = questionDTO.getAnswers().get(selectedAnswerIndex);

        return selectedAnswer.getCorrect();
    }

    int numberOfCorrectAnswers(ResultDTO resultDTO) {
        return resultDTO.getQuestionResults().stream().filter(
                questionResultDTO -> questionResultDTO.getCorrect()).collect(Collectors.toList()).size();
    }
}
