package com.exxeta.questionaire;

import com.exxeta.questionaire.domain.AnswerAnalyser;
import com.exxeta.questionaire.domain.FileReader;
import com.exxeta.questionaire.domain.QuestionCreator;
import com.exxeta.questionaire.domain.QuestionParser;
import com.exxeta.questionaire.model.QuestionDTO;
import com.exxeta.questionaire.model.QuestionWrapperDTO;
import com.exxeta.questionaire.model.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class QuestionnaireController {

	@Autowired
	private FileReader fileReader;

	@Autowired
	private QuestionParser questionParser;

	@Autowired
	private QuestionCreator questionCreator;

	@Autowired
	private AnswerAnalyser answerAnalyser;

	@GetMapping("/")
	public String questionnaire(Model model) {
		List<String> lines = fileReader.readFile("questionnaire.txt");
		List<List<String>> aggregatedQuestions = questionParser.parse(lines);
		QuestionWrapperDTO questionWrapper = questionCreator.createQuestions(aggregatedQuestions);
		model.addAttribute("questionWrapper", questionWrapper);
		return "questionnaire";
	}

	@PostMapping("/")
	public String questionnaire(@ModelAttribute(name = "questionWrapper") QuestionWrapperDTO questionWrapper, Model model) {
		List<QuestionDTO> questions = questionWrapper.getQuestions();
		ResultDTO resultDTO = answerAnalyser.Analyse(questions);
		model.addAttribute("resultDTO", resultDTO);
		return "results";
	}

}
