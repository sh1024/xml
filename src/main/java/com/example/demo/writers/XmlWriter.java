package com.example.demo.writers;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class XmlWriter {

    public void writeToFile(String filename, String xml) throws IOException {
        Files.write(Paths.get(filename), xml.getBytes());
    }
}
