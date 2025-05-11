package org.model;

public class CardTapRecord {
    public int id;
    public String dateTimeUTC;
    public String tapType; // ON or OFF
    public String stopId;
    public String companyId;
    public String busId;
    public String pan;

    public CardTapRecord(int id, String dateTimeUTC, String tapType, String stopId, String companyId, String busId,
            String pan) {
        this.id = id;
        this.dateTimeUTC = dateTimeUTC;
        this.tapType = tapType;
        this.stopId = stopId;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
    }
}