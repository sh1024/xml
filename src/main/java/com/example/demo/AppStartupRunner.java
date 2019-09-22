package com.example.demo;

import com.example.demo.model.Metadata;
import com.example.demo.parsers.NoticeParser;
import com.example.demo.writers.GsonWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private NoticeParser noticeParser;
    private GsonWriter gsonWriter;

    @Value("${document}")
    private String documentName;
    @Value("${notice}")
    private String noticeName;

    public AppStartupRunner(NoticeParser noticeParser, GsonWriter gsonWriter) {
        this.noticeParser = noticeParser;
        this.gsonWriter = gsonWriter;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            Metadata metadata = noticeParser.parseNotice(noticeName);
            gsonWriter.writeJsonToFile("notice.json", metadata);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}