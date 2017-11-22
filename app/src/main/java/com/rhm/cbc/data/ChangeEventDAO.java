package com.rhm.cbc.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.rhm.cbc.data.model.ChangeEvent;
import com.rhm.cbc.data.model.ChangeGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by sambo on 10/29/17.
 */
@Dao
public interface ChangeEventDAO {

    @Query("SELECT * FROM changeevent ORDER BY id DESC")
    List<ChangeEvent> getAll();

    @Query("SELECT * FROM changeevent ORDER BY id DESC")
    Flowable<ChangeEvent> getAllFlowable();

    @Query("SELECT * FROM changeevent WHERE yearMonthDay = :yearMonthDay ORDER BY id DESC")
    List<ChangeEvent> getEventsByYearMonthDay(int yearMonthDay);

    @Query("SELECT yearMonthDay, eventTime, COUNT(*) AS count from changeevent GROUP BY yearMonthDay ORDER BY yearMonthDay DESC")
    List<ChangeGroup> getAllGroups();

    @Query("SELECT yearMonthDay, eventTime, COUNT(*) AS count from changeevent GROUP BY yearMonthDay ORDER BY yearMonthDay DESC")
    Flowable<ChangeGroup> getAllGroupsFlowable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(ChangeEvent... changeEvent);

    @Insert
    Long insert(ChangeEvent changeEvent);

    @Query("DELETE FROM changeevent")
    void wipeTable();

    @Query("SELECT * FROM changeevent WHERE yearMonthDay = :yearMonthDay ORDER BY id DESC")
    Flowable<ChangeEvent> getEventsByYearMonthDayFlowable(int yearMonthDay);

    @Query("SELECT COUNT(*) FROM changeevent GROUP BY yearMonthDay ORDER BY yearMonthDay DESC LIMIT 0,1")
    Flowable<Integer> getAllEventCount();
}
