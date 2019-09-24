package com.example.demo;

import com.example.demo.parsers.DocumentParser;
import com.example.demo.utils.DomParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathExpressionException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;

@RunWith(JUnit4.class)
public class DocumentParserTest {

    private static final String PROCESSING_INSTRUCTION_CLG_MDFC = "//processing-instruction('CLG.MDFC')";
    private static final String PROCESSING_INSTRUCTION_CLG_MDFO = "//processing-instruction('CLG.MDFO')";

    @Test
    public void shouldCorrectlyCleanInstructionsFromXml()
            throws XMLStreamException, IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        Path path = Paths.get("src", "main", "resources", "xml", "document.xml");
        DomParser domParser = new DomParser(path);
        assertThat(domParser.getFirstNodeValue(PROCESSING_INSTRUCTION_CLG_MDFC), not(isEmptyOrNullString()));
        assertThat(domParser.getFirstNodeValue(PROCESSING_INSTRUCTION_CLG_MDFO), not(isEmptyOrNullString()));

        DocumentParser documentParser = new DocumentParser();
        documentParser.cleanProcessingInstructions("document.xml", "new_document.xml");

        path = Paths.get("new_document.xml");
        domParser = new DomParser(path);
        assertThat(domParser.getFirstNodeValue(PROCESSING_INSTRUCTION_CLG_MDFC), isEmptyOrNullString());
        assertThat(domParser.getFirstNodeValue(PROCESSING_INSTRUCTION_CLG_MDFO), isEmptyOrNullString());
    }
}
