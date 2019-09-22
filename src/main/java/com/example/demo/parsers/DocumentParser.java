package com.example.demo.parsers;

import org.springframework.stereotype.Component;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.StringWriter;

@Component
public class DocumentParser {

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    public String cleanProcessingInstructions(String filename) throws XMLStreamException {

        XMLEventReader xmlEventReader = FACTORY.createXMLEventReader(
                this.getClass().getClassLoader().getResourceAsStream("xml/" + filename));

        StringWriter buf = new StringWriter(1024);
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.getEventType() == XMLEvent.PROCESSING_INSTRUCTION){
                continue;
            }
            event.writeAsEncodedUnicode(buf);
        }
        // reader.close()
        return buf.toString();
    }
}


