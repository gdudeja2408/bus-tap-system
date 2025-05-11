package org.app;

import org.model.CardTapRecord;
import org.model.TripRecord;
import org.service.FareService;
import org.service.RecordProcessor;
import org.service.RecordReader;
import org.service.RecordWriter;

import java.util.*;

public class MainProgram {
    public static void main(String[] args) throws Exception {
        String inputFilePath = "src/main/resources/taps.csv";
        String outputFilePath = "src/main/resources/trips.csv";

        RecordReader recordReader = new RecordReader(inputFilePath);
        List<CardTapRecord> tapRecords = recordReader.read();

        // Process the tap records into trip records
        RecordProcessor recordProcessor = new RecordProcessor(new FareService());
        List<TripRecord> trips = recordProcessor.process(tapRecords);

        // Write the trip records to the output file
        RecordWriter recordWriter = new RecordWriter(outputFilePath);
        recordWriter.write(trips);
    }
}
