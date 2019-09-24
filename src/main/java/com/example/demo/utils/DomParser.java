package com.example.demo.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
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

import static java.util.Optional.ofNullable;

public class DomParser {
    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    private static final XPathFactory X_PATH_FACTORY = XPathFactory.newInstance();

    private XPath xPath = X_PATH_FACTORY.newXPath();
    private Document document;

    public DomParser(Path path) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
        try (InputStream in = Files.newInputStream(path)) {
            document = builder.parse(in);
        }
    }

    public String getFirstNodeValue(String expression) throws XPathExpressionException {
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
        return ofNullable(nodeList.item(0)).map(Node::getNodeValue).orElse("");
    }
}
