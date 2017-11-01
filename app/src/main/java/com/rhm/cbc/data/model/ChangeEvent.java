package com.rhm.cbc.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ChangeEvent implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String typeName;
    String ssid;
    String detailedState;
    String eventTime;
    String completeMsg;
    int yearMonthDay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getDetailedState() {
        return detailedState;
    }

    public void setDetailedState(String detailedState) {
        this.detailedState = detailedState;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getCompleteMsg() {
        return completeMsg;
    }

    public void setCompleteMsg(String completeMsg) {
        this.completeMsg = completeMsg;
    }

    public int getYearMonthDay() {
        return yearMonthDay;
    }

    public void setYearMonthDay(int yearMonthDay) {
        this.yearMonthDay = yearMonthDay;
    }
}
