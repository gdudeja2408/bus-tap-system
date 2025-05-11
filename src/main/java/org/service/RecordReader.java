package org.service;

import org.model.CardTapRecord;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class RecordReader {
    private final String inputFilePath;

    public RecordReader(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public List<CardTapRecord> read() throws IOException {
        List<CardTapRecord> cardTaps = new ArrayList<>();
        Path logPath = Paths.get(inputFilePath).toAbsolutePath();

        if (!Files.exists(logPath)) {
            throw new IOException("File does not exist: " + logPath);
        }

        List<String> lines = Files.readAllLines(logPath);
        lines.remove(0); // Remove header

        for (String line : lines) {
            String[] parts = line.split(",");
            cardTaps.add(new CardTapRecord(
                    Integer.parseInt(parts[0].trim()),
                    parts[1].trim(),
                    parts[2].trim(),
                    parts[3].trim(),
                    parts[4].trim(),
                    parts[5].trim(),
                    parts[6].trim()));
        }
        return cardTaps;
    }
}