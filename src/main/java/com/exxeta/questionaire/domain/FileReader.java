package com.exxeta.questionaire.domain;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileReader {

    public List<String> readFile() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of("questionnaire.txt"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        lines.removeIf(s -> s.isEmpty());
        return lines;
    }
}
