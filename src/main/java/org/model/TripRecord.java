package org.model;

public class TripRecord {
    public String started;
    public String finished;
    public long durationSecs;
    public String fromStopId;
    public String toStopId;
    public String chargeAmount;
    public String companyId;
    public String busId;
    public String pan;
    public String status;

    public TripRecord(String started, String finished, long durationSecs, String fromStopId, String toStopId,
            String chargeAmount, String companyId, String busId, String pan, String status) {
        this.started = started;
        this.finished = finished;
        this.durationSecs = durationSecs;
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.chargeAmount = chargeAmount;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
        this.status = status;
    }

    public String getFinished() {
        return finished;
    }

    public long getDurationSecs() {
        return durationSecs;
    }

    public String getFromStopId() {
        return fromStopId;
    }

    public String getToStopId() {
        return toStopId;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getBusId() {
        return busId;
    }

    public String getPan() {
        return pan;
    }

    public String getStatus() {
        return status;
    }

    public String getStarted() {
        return started;
    }

    @Override
    public String toString() {
        return String.join(", ", started, finished, String.valueOf(durationSecs), fromStopId, toStopId, chargeAmount,
                companyId, busId, pan, status);
    }
}

