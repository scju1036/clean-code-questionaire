package com.exxeta.questionaire;

import com.exxeta.questionaire.interactors.Interactor;
import com.exxeta.questionaire.model.QuestionWrapperDTO;
import com.exxeta.questionaire.model.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionnaireController {

	@Autowired
	private Interactor interactor;

	@GetMapping("/")
	public String questionnaire(Model model) {
		QuestionWrapperDTO questionWrapper = interactor.start("questionnaire.txt");
		model.addAttribute("questionWrapper", questionWrapper);
		return "questionnaire";
	}

	@PostMapping("/")
	public String questionnaire(@ModelAttribute(name = "questionWrapper") QuestionWrapperDTO questionWrapper, Model model) {
		ResultDTO resultDTO = interactor.showScore(questionWrapper);
		model.addAttribute("resultDTO", resultDTO);
		return "results";
	}

}
