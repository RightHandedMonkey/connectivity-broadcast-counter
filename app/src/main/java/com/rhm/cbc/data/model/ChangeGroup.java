package com.rhm.cbc.data.model;

import android.arch.persistence.room.ColumnInfo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeGroup implements Serializable{
    @ColumnInfo(name = "yearMonthDay")
    public int yearMonthDay;

    @ColumnInfo(name = "eventTime")
    public String eventTime;

    @ColumnInfo(name = "count")
    public int count;

    public String getFormattedDate() {
        String formattedDate = "Could not parse date";
        SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = parser.parse(yearMonthDay+"");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = formatter.format(date);
        } catch (ParseException e) {
        }
        return formattedDate;
    }

}
