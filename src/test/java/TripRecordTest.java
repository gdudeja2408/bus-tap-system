import org.junit.jupiter.api.Test;
import org.model.CardTapRecord;
import org.model.TripRecord;
import org.service.FareService;
import org.service.RecordProcessor;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripRecordTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final FareService fareService = new FareService(); // Mock or real implementation

    private final RecordProcessor recordProcessor = new RecordProcessor(fareService);

    @Test
    public void testCreateCompletedTripRecord() {
        // Arrange
        CardTapRecord onCardTap = new CardTapRecord(
                1, "22-01-2023 13:00:00", "ON", "Stop1", "Company1", "Bus1", "5500005555555559"
        );
        CardTapRecord offCardTap = new CardTapRecord(
                2, "22-01-2023 13:10:00", "OFF", "Stop2", "Company1", "Bus1", "5500005555555559"
        );

        TripRecord trip = recordProcessor.createTripRecord(onCardTap, offCardTap, formatter);

        assertEquals("COMPLETED", trip.getStatus());
        assertEquals("22-01-2023 13:00:00", trip.getStarted());
        assertEquals("22-01-2023 13:10:00", trip.getFinished());
        assertEquals("Stop1", trip.getFromStopId());
        assertEquals("Stop2", trip.getToStopId());
        assertEquals(600, trip.getDurationSecs()); // Duration in seconds
        assertEquals("$3.25", trip.getChargeAmount()); // Assuming the fare for Stop1 to Stop2 is $3.25
    }

    @Test
    public void testCreateCancelledTripRecord() {
        // Arrange
        CardTapRecord onCardTap = new CardTapRecord(
                1, "22-01-2023 13:00:00", "ON", "Stop1", "Company1", "Bus1", "5500005555555559"
        );
        CardTapRecord offCardTap = new CardTapRecord(
                2, "22-01-2023 13:05:00", "OFF", "Stop1", "Company1", "Bus1", "5500005555555559"
        );

        TripRecord trip = recordProcessor.createTripRecord(onCardTap, offCardTap, formatter);

        assertEquals("CANCELLED", trip.getStatus());
        assertEquals("22-01-2023 13:00:00", trip.getStarted());
        assertEquals("22-01-2023 13:05:00", trip.getFinished());
        assertEquals("Stop1", trip.getFromStopId());
        assertEquals("Stop1", trip.getToStopId());
        assertEquals(300, trip.getDurationSecs());
        assertEquals("$0.00", trip.getChargeAmount());
    }

    @Test
    public void testCreateIncompleteTripRecord() {
        CardTapRecord onCardTap = new CardTapRecord(
                1, "22-01-2023 13:00:00", "ON", "Stop1", "Company1", "Bus1", "5500005555555559"
        );

        TripRecord trip = recordProcessor.createTripRecord(onCardTap, null, formatter);

        assertEquals("INCOMPLETE", trip.getStatus());
        assertEquals("22-01-2023 13:00:00", trip.getStarted());
        assertEquals("", trip.getFinished());
        assertEquals("Stop1", trip.getFromStopId());
        assertEquals("", trip.getToStopId());
        assertEquals(0, trip.getDurationSecs());
        assertEquals("$7.30", trip.getChargeAmount());
    }
}