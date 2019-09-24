package com.example.demo;

import com.example.demo.model.Metadata;
import com.example.demo.transformers.XslTransformer;
import com.example.demo.utils.DomParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class TransformerTest {

    @Test
    public void shouldCorrectlyTransformAndGetResultFile()
            throws IOException, TransformerException, ParserConfigurationException, SAXException, XPathExpressionException {

        XslTransformer xslTransformer = new XslTransformer(
                TransformerFactory.newInstance());

        Metadata metadata = new Metadata();
        metadata.setCreationDate(
                OffsetDateTime.of(1001, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
        metadata.setModificationDate(
                OffsetDateTime.of(2001, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
        metadata.setDocumentTitle("Document super title");

        xslTransformer.transform(metadata, "new_document.xml", "doc.xsl", "result.xml");

        Path file = Paths.get( "result.xml");
        DomParser domParser = new DomParser(file);
        assertThat(domParser.getFirstNodeValue("//creation-date/text()"), is("1001-01-01T00:00Z"));
        assertThat(domParser.getFirstNodeValue("//modification-date/text()"), is("2001-01-01T00:00Z"));
        assertThat(domParser.getFirstNodeValue("//document-title/text()"), is("Document super title"));
        assertThat(domParser.getFirstNodeValue("//title/text()"), is("SECTION 1 - Preliminary provisions and scope"));
    }
}
