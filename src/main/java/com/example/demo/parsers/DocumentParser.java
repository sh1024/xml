package com.example.demo.parsers;

import com.example.demo.utils.StaxReader;
import com.example.demo.utils.StaxWriter;
import org.springframework.stereotype.Component;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DocumentParser {

    public void cleanProcessingInstructions(String filename, String fileToWrite)
            throws XMLStreamException, IOException {
        Path pathToFile = Paths.get("src", "main", "resources", "xml", filename);

        StaxReader staxReader = new StaxReader(pathToFile);
        StaxWriter staxWriter = new StaxWriter(fileToWrite);

        try (staxReader; staxWriter) {
            while (staxReader.getReader().hasNext()) {
                XMLEvent event = staxReader.getReader().nextEvent();
                if (event.getEventType() == XMLEvent.PROCESSING_INSTRUCTION) {
                    continue;
                }
                staxWriter.getWriter().add(event);
            }
        }
    }
}


