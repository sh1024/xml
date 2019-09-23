package com.example.demo;

import com.example.demo.parsers.DocumentParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import java.io.IOException;

@RunWith(JUnit4.class)
public class DocumentParserTest {

    @Test
    public void shouldCorrectlyCleanInstructionsFromXml() throws XMLStreamException, IOException, ParserConfigurationException, SAXException {
        DocumentParser documentParser = new DocumentParser();
        documentParser.cleanProcessingInstructions("document.xml", "new_document.xml");

        // read as string
        // assertThat(result, not(containsString("<?CLG.MDFC")));
        // assertThat(result, not(containsString("<?CLG.MDFO")));
    }
}
