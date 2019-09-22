package com.example.demo;

import com.example.demo.model.Metadata;
import com.example.demo.writers.GsonWriter;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class GsonWriterTest {

    private static final String NOTICE_JSON_FILENAME = "notice.json";

    @Test
    public void shouldCorrectlyWriteJsonFromMetadata() throws IOException {
        GsonWriter writer = new GsonWriter();

        Metadata metadata = new Metadata();
        metadata.setDocumentTitle("document!");
        OffsetDateTime dateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        metadata.setCreationDate(dateTime);
        metadata.setModificationDate(dateTime);
        writer.writeJsonToFile(NOTICE_JSON_FILENAME, metadata);

        Metadata result;
        try (FileReader reader = new FileReader(NOTICE_JSON_FILENAME)) {
            Gson gson = new Gson();
            result = gson.fromJson(reader, Metadata.class);
        }
        assertThat(result.getDocumentTitle(), is("document!"));
        assertThat(result.getModificationDate(), is(dateTime));
        assertThat(result.getCreationDate(), is(dateTime));
    }
}
