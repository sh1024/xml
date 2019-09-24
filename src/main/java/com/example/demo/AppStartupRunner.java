package com.example.demo;

import com.example.demo.model.Metadata;
import com.example.demo.parsers.DocumentParser;
import com.example.demo.parsers.NoticeParser;
import com.example.demo.transformers.XslTransformer;
import com.example.demo.writers.GsonWriter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private NoticeParser noticeParser;
    private GsonWriter gsonWriter;
    private DocumentParser documentParser;
    private XslTransformer transformer;

    @Value("${document}")
    private String documentName;
    @Value("${notice}")
    private String noticeName;

    public AppStartupRunner(NoticeParser noticeParser,
                            GsonWriter gsonWriter,
                            DocumentParser documentParser,
                            XslTransformer transformer) {
        this.noticeParser = noticeParser;
        this.gsonWriter = gsonWriter;
        this.documentParser = documentParser;
        this.transformer = transformer;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            Metadata metadata = noticeParser.parseNotice(noticeName);
            gsonWriter.writeJsonToFile("notice.json", metadata);
            documentParser.cleanProcessingInstructions(documentName, "new_document.xml");
            // json reader
            Metadata new_metadata;
            try (FileReader reader = new FileReader("notice.json")) {
                Gson gson = new Gson();
                new_metadata = gson.fromJson(reader, Metadata.class);
            }
            transformer.transform(new_metadata,"new_document.xml", "doc.xsl", "result.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Success!");
    }
}