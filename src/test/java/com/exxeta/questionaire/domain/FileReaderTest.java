package com.exxeta.questionaire.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class FileReaderTest {

    private FileReader fileReader;

    @Before
    public void before() {
        this.fileReader = new FileReader();
    }

    @Test
    public void readFileWithPresentFileTest() {
        // execute test
        List<String> lines  = fileReader.readFile("questionnaireTest.txt");

        // verify result
        assertThat(lines).isNotNull().hasSize(4);
        assertThat(lines.get(0)).isEqualTo("?Which of these animals is a mammal");
        assertThat(lines.get(1)).isEqualTo("Ant");
        assertThat(lines.get(2)).isEqualTo("Bee");
        assertThat(lines.get(3)).isEqualTo("*Cat");
    }


    @Test(expected = IOException.class)
    public void readFileWithoutPresentFileTest() {
        // execute test
        List<String> lines  = fileReader.readFile("notPresent.txt");

        // verify result
        fail();
    }
}
