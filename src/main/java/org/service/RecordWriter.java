package org.service;

import org.model.TripRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecordWriter {
    private final String outputFilePath;

    public RecordWriter(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public void write(List<TripRecord> trips) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status");
        lines.addAll(trips.stream().map(TripRecord::toString).collect(Collectors.toList()));
        Files.write(Paths.get(outputFilePath), lines);
        System.out.println("Records generated are in file " + outputFilePath);
    }
}
