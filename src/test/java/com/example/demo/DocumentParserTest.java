package com.example.demo;

import com.example.demo.parsers.DocumentParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.stream.XMLStreamException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class DocumentParserTest {

    @Test
    public void shouldCorrectlyCleanInstructionsFromXml() throws XMLStreamException {
        DocumentParser documentParser = new DocumentParser();
        String result = documentParser.cleanProcessingInstructions("document.xml");

        assertThat(result, not(containsString("<?CLG.MDFC")));
        assertThat(result, not(containsString("<?CLG.MDFO")));
    }
}
