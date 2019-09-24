package com.example.demo.parsers;

import com.example.demo.model.Metadata;
import com.example.demo.utils.DomParser;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
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

    public Metadata parseNotice(String filename) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        if (Strings.isBlank(filename)) {
            throw new RuntimeException("File name can't be null");
        }
        Path pathToFile = Paths.get("src","main", "resources", "xml", filename);
        DomParser domParser = new DomParser(pathToFile);

        OffsetDateTime creationDate = OffsetDateTime.parse(domParser.getFirstNodeValue(CREATION_DATE_EXPRESSION), formatter);
        OffsetDateTime modificationDate = OffsetDateTime.parse(domParser.getFirstNodeValue(MODIFICATION_DATE_EXPRESSION), formatter);
        String documentTitle = domParser.getFirstNodeValue(DOCUMENT_TITLE_EXPRESSION);

        Metadata metadata = new Metadata();
        metadata.setCreationDate(creationDate);
        metadata.setModificationDate(modificationDate);
        metadata.setDocumentTitle(documentTitle);

        return metadata;
    }
}
