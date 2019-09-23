package com.example.demo;

import com.example.demo.writers.XmlWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class XmlWriterTest {

    @Test
    public void shouldCorrectlyWriteXmlToFile() throws IOException {
        XmlWriter xmlWriter = new XmlWriter();
        String stringToWrite = "<tag1></tag1";
        xmlWriter.writeToFile("new_document.xml", stringToWrite);

        Path path = Paths.get("new_document.xml");
        String read = Files.readAllLines(path).get(0);
        assertThat(read, is(stringToWrite));
    }
}
