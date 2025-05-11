package org.service;

import org.model.CardTapRecord;

import java.util.Map;
import java.util.Set;

public class FareService {

    private static final Map<Set<String>, Double> TRIP_COSTS =
            Map.of(Set.of("Stop1", "Stop2"), 3.25, Set.of("Stop2", "Stop3"), 5.50, Set.of("Stop1", "Stop3"), 7.30);

    public static String calculateCharge(CardTapRecord onTap, CardTapRecord offTap, String status) {
        if ("CANCELLED".equals(status))
            return "$0.00";

        if ("COMPLETED".equals(status)) {
            Set<String> tripStops = Set.of(onTap.stopId, offTap.stopId);
            return "$" + TRIP_COSTS.get(tripStops);
        }

        if ("INCOMPLETE".equals(status)) {
            String fromStop = (onTap != null) ? onTap.stopId : offTap.stopId;
            double maxCharge = TRIP_COSTS.entrySet().stream().filter(entry -> entry.getKey().contains(fromStop))
                    .mapToDouble(Map.Entry::getValue).max().orElse(0);
            String formattedCharge = String.format("%.2f", maxCharge);
            return "$" + formattedCharge;
        }

        return "$0.00";
    }
}
