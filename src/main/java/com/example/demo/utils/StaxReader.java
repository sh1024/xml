package com.example.demo.utils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaxReader implements AutoCloseable {

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLEventReader reader;
    private final InputStream inputStream;

    public StaxReader(Path path) throws XMLStreamException, IOException {
        inputStream = Files.newInputStream(path);
        reader = FACTORY.createXMLEventReader(inputStream);
    }

    public XMLEventReader getReader() {
        return reader;
    }

    @Override
    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            //
        }
    }
}
