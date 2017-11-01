package com.rhm.cbc.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.rhm.cbc.data.model.ChangeEvent;

import java.util.List;

/**
 * Created by sambo on 10/29/17.
 */
@Dao
public interface ChangeEventDAO {

        @Query("SELECT * FROM changeevent ORDER BY id ASC")
        List<ChangeEvent> getAll();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        List<Long> insert(ChangeEvent... changeEvent);

        @Insert
        Long insert(ChangeEvent changeEvent);

        @Query("DELETE FROM changeevent")
        void wipeTable();

}
