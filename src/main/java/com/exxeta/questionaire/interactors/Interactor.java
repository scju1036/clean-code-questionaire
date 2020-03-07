package com.exxeta.questionaire.interactors;

import com.exxeta.questionaire.domain.AnswerAnalyser;
import com.exxeta.questionaire.domain.FileReader;
import com.exxeta.questionaire.domain.QuestionCreator;
import com.exxeta.questionaire.domain.QuestionParser;
import com.exxeta.questionaire.model.QuestionDTO;
import com.exxeta.questionaire.model.QuestionWrapperDTO;
import com.exxeta.questionaire.model.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Interactor {

    @Autowired
    private FileReader fileReader;

    @Autowired
    private QuestionParser questionParser;

    @Autowired
    private QuestionCreator questionCreator;

    @Autowired
    private AnswerAnalyser answerAnalyser;

    public QuestionWrapperDTO start(String fileName) {
        List<String> lines = fileReader.readFile(fileName);
        List<List<String>> aggregatedQuestions = questionParser.parse(lines);
        QuestionWrapperDTO questionWrapper = questionCreator.createQuestions(aggregatedQuestions);
        return questionWrapper;
    }

    public ResultDTO showScore(QuestionWrapperDTO questionWrapper) {
        List<QuestionDTO> questions = questionWrapper.getQuestions();
        ResultDTO resultDTO = answerAnalyser.analyse(questions);
        return resultDTO;
    }


}
