package com.rhm.cbc.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rhm.cbc.data.model.ChangeEvent;

/**
 * Created by sambo on 10/29/17.
 */

@Database(entities = {ChangeEvent.class}, version = 1)
public abstract class CBCDatabase extends RoomDatabase {
        public abstract ChangeEventDAO changeEventDao();

        private static CBCDatabase ourInstance;

        public static CBCDatabase getInstance(Context appContext) {
            if (ourInstance == null) {
                ourInstance = Room.databaseBuilder(appContext,
                        CBCDatabase.class, "CBCDb")
                        //.addMigrations(MIGRATION_1_2)
                        .build();
            }
            return ourInstance;
        }

//        static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//            @Override
//            public void migrate(SupportSQLiteDatabase database) {
//                Log.d("SAMB", "Running database migration");
//            }
//        };
}
