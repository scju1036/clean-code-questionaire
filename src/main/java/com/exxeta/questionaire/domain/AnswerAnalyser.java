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

    public ResultDTO Analyse(List<QuestionDTO> questions){
        ResultDTO resultDTO = new ResultDTO();

        for (QuestionDTO question : questions) {
            QuestionResultDTO questionResultDTO = new QuestionResultDTO();

            // Set the original question/sentence
            questionResultDTO.setSentence(question.getSentence());

            // Get All Answers
            List<AnswerDTO> answers = question.getAnswers();

            // Get Correct answer Index
            Optional<AnswerDTO> correctAnswerOptional = answers.stream()
                                            .filter(answerDTO -> answerDTO.getCorrect())
                                            .findFirst();
            AnswerDTO correctAnswer = correctAnswerOptional.get();

            // Set correct answer sentence
            questionResultDTO.setCorrectAnswer(correctAnswer.getSentence());

            // Set the selected Answer Sentence and Correctness
            // If no answer was selected, then the answer is wrong
            if(question.getSelectedAnswer() == null) {
                questionResultDTO.setCorrect(false);
                questionResultDTO.setSelectedAnswer("");
            }else{ // Otherwise, pull the answer and check if it was the correct one.
                Integer selectedAnswerIndex = Math.toIntExact(question.getSelectedAnswer());
                AnswerDTO selectedAnswer = answers.get(selectedAnswerIndex);

                questionResultDTO.setCorrect(selectedAnswer.getCorrect());
                questionResultDTO.setSelectedAnswer(selectedAnswer.getSentence());
            }

            resultDTO.getQuestionResults().add(questionResultDTO);
        }

        // Set the total number of questions
        resultDTO.setTotalQuestions(questions.size());

        // Set the total number of correct answers
        Integer totalCorrect = resultDTO.getQuestionResults().stream().filter(
                questionResultDTO -> questionResultDTO.getCorrect()).collect(Collectors.toList()).size();
        resultDTO.setTotalCorrect(totalCorrect);

        return resultDTO;
    }

}
