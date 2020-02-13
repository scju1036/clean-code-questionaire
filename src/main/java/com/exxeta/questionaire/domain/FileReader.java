package com.exxeta.questionaire.domain;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileReader {

    public List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of(fileName));
        }
        catch (IOException ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing questions file");
        }
        lines.removeIf(s -> s.isEmpty());
        return lines;
    }
}
