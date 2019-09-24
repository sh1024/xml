package com.example.demo.transformers;

import com.example.demo.model.Metadata;
import org.springframework.stereotype.Component;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class XslTransformer {

    private TransformerFactory transformerFactory;

    public XslTransformer(TransformerFactory transformerFactory) {
        this.transformerFactory = transformerFactory;
    }

    public void transform(Metadata metadata, String documentName, String xsl, String resultFileName)
            throws TransformerException, IOException {

        Path pathToXsl = Paths.get("src", "main", "resources", "xslt", xsl);
        Path documentSource = Paths.get(documentName);

        InputStream style = Files.newInputStream(pathToXsl);
        InputStream document = Files.newInputStream(documentSource);
        try (style; document) {
            StreamSource styleSource = new StreamSource(style);
            StreamSource streamSource = new StreamSource(document);
            StreamResult result = new StreamResult(new File(resultFileName));

            Transformer transformer = transformerFactory.newTransformer(styleSource);
            transformer.setParameter("creation-date", metadata.getCreationDate());
            transformer.setParameter("modification-date", metadata.getModificationDate());
            transformer.setParameter("document-title", metadata.getDocumentTitle());

            transformer.transform(streamSource, result);
        }
    }
}

