package com.exxeta.questionaire.domain;

import com.exxeta.questionaire.model.ParserConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionParser {

    public List<List<String>> parse(List<String> lines) {
        List<Integer> indices = getIndicesOfQuestions(lines);
        List<List<String>> aggregatedQuestions = aggregateQuestions(indices, lines);
        return aggregatedQuestions;
    }

    List<Integer> getIndicesOfQuestions(List<String> lines) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(ParserConfig.QUESTION_MARKER.getCharacter())) {
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
}
