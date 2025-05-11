package org.service;

import org.model.CardTapRecord;
import org.model.TripRecord;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RecordProcessor {
    private final FareService fareService;

    public RecordProcessor(FareService fareService) {
        this.fareService = fareService;
    }

    public List<TripRecord> process(List<CardTapRecord> cardTaps) {
        List<TripRecord> travels = new ArrayList<>();
        Map<String, List<CardTapRecord>> activeTaps = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (CardTapRecord cardTap : cardTaps) {
            if (cardTap.tapType.equals("ON")) {
                activeTaps.putIfAbsent(cardTap.pan, new ArrayList<>());
                activeTaps.get(cardTap.pan).add(cardTap);
            } else if (cardTap.tapType.equals("OFF")) {
                List<CardTapRecord> onCardTaps = activeTaps.get(cardTap.pan);
                if (onCardTaps != null && !onCardTaps.isEmpty()) {
                    CardTapRecord onCardTap = onCardTaps.remove(onCardTaps.size() - 1);
                    travels.add(createTripRecord(onCardTap, cardTap, formatter));
                    if (onCardTaps.isEmpty()) {
                        activeTaps.remove(cardTap.pan);
                    }
                }
            }
        }

        for (Map.Entry<String, List<CardTapRecord>> entry : activeTaps.entrySet()) {
            for (CardTapRecord onCardTap : entry.getValue()) {
                travels.add(createTripRecord(onCardTap, null, formatter));
            }
        }
        return travels;
    }

    public TripRecord createTripRecord(CardTapRecord onCardTap, CardTapRecord offCardTap, DateTimeFormatter formatter) {
        String finished = (offCardTap != null) ? offCardTap.dateTimeUTC : "";
        String toStopId = (offCardTap != null) ? offCardTap.stopId : "";
        long duration = (offCardTap != null)
                ? Duration.between(
                LocalDateTime.parse(onCardTap.dateTimeUTC, formatter),
                LocalDateTime.parse(offCardTap.dateTimeUTC, formatter)
        ).getSeconds()
                : 0;
        String status = (offCardTap == null) ? "INCOMPLETE" :
                (onCardTap.stopId.equals(offCardTap.stopId) ? "CANCELLED" : "COMPLETED");
        String charge = fareService.calculateCharge(onCardTap, offCardTap, status);

        return new TripRecord(
                onCardTap.dateTimeUTC,
                finished,
                duration,
                onCardTap.stopId,
                toStopId,
                charge,
                onCardTap.companyId,
                onCardTap.busId,
                onCardTap.pan,
                status
        );
    }
}