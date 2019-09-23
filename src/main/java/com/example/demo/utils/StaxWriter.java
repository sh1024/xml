package com.example.demo.utils;

import javax.xml.stream.*;
import java.io.FileWriter;
import java.io.IOException;

public class StaxWriter implements AutoCloseable {

    private static final XMLOutputFactory FACTORY = XMLOutputFactory.newInstance();
    private final XMLEventWriter writer;
    private final FileWriter fwr;

    public StaxWriter(String fileName) throws XMLStreamException, IOException {
        fwr = new FileWriter(fileName);
        writer = FACTORY.createXMLEventWriter(fwr);
    }

    public XMLEventWriter getWriter() {
        return writer;
    }

    @Override
    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (fwr != null) {
                fwr.close();
            }
        } catch (Exception e) {
            //
        }
    }
}
