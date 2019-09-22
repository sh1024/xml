package com.example.demo.writers;

import com.example.demo.model.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@Component
public class GsonWriter {

    public void writeJsonToFile(String filename, Metadata metadata) throws IOException {

        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(metadata, writer);
        }
    }
}
