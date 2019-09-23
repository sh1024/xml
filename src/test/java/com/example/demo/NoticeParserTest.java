package com.example.demo;

import com.example.demo.model.Metadata;
import com.example.demo.parsers.NoticeParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.example.demo.parsers.NoticeParser.formatter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class NoticeParserTest {

    private static final String DOCUMENT_NAME_EXPECTED = "2004/870/EC: Council decision of 29 April 2004 concerning the " +
            "conclusion of the Cooperation Agreement between the European Community and the Islamic Republic of Pakistan";

    @Test
    public void shouldCorrectlyRetrieveMetadataFromXml()
            throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        NoticeParser noticeParser = new NoticeParser(DocumentBuilderFactory.newInstance(),
                XPathFactory.newInstance());

        Metadata metadata = noticeParser.parseNotice("notice.xml");
        assertThat(metadata.getCreationDate(),
                is(OffsetDateTime.of(1001, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)));

        String lastModificationDateExpected = "2018-06-01T20:10:58.641+02:00";
        OffsetDateTime expected = OffsetDateTime.parse(lastModificationDateExpected, formatter);
        assertThat(metadata.getModificationDate(),
                is(expected));

        assertThat(metadata.getDocumentTitle(), is(DOCUMENT_NAME_EXPECTED));

    }
}

