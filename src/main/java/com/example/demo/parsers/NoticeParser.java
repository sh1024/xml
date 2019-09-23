package com.example.demo.parsers;

import com.example.demo.model.Metadata;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class NoticeParser {

    public static DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private static final String CREATION_DATE_EXPRESSION = "//CREATIONDATE/VALUE/text()";
    private static final String MODIFICATION_DATE_EXPRESSION = "//LASTMODIFICATIONDATE/VALUE/text()";
    private static final String DOCUMENT_TITLE_EXPRESSION =
            "//EXPRESSION_TITLE/VALUE[../../EXPRESSION_USES_LANGUAGE/IDENTIFIER/text() = \"ENG\"]/text()";

    private DocumentBuilderFactory builderFactory;
    private XPathFactory xPathFactory;

    public NoticeParser(DocumentBuilderFactory builderFactory, XPathFactory xPath) {
        this.builderFactory = builderFactory;
        this.xPathFactory = xPath;
    }

    public Metadata parseNotice(String filename) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        XPath xPath = xPathFactory.newXPath();

        Path pathToFile = Paths.get("src","main", "resources", "xml", filename);
        Document xmlDocument;
        try (InputStream in = Files.newInputStream(pathToFile)) {
             xmlDocument = builder.parse(in);
        }

        NodeList creationDateList = (NodeList) xPath.compile(CREATION_DATE_EXPRESSION).evaluate(xmlDocument, XPathConstants.NODESET);
        OffsetDateTime creationDate = OffsetDateTime.parse(creationDateList.item(0).getNodeValue(), formatter);

        NodeList modificationDateList = (NodeList) xPath.compile(MODIFICATION_DATE_EXPRESSION).evaluate(xmlDocument, XPathConstants.NODESET);
        OffsetDateTime modificationDate = OffsetDateTime.parse(modificationDateList.item(0).getNodeValue(), formatter);

        NodeList documentTitleList = (NodeList) xPath.compile(DOCUMENT_TITLE_EXPRESSION).evaluate(xmlDocument, XPathConstants.NODESET);
        String documentTitle = documentTitleList.item(0).getNodeValue();

        Metadata metadata = new Metadata();
        metadata.setCreationDate(creationDate);
        metadata.setModificationDate(modificationDate);
        metadata.setDocumentTitle(documentTitle);

        return metadata;
    }
}
