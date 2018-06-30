package com.erodragodev.www.journalapp.data;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.erodragodev.www.journalapp.DateConverter;
import com.erodragodev.www.journalapp.data.model.JournalDao;
import com.erodragodev.www.journalapp.data.model.MyJournal;

@android.arch.persistence.room.Database(
        entities = {MyJournal.class},
        version = 1,
        exportSchema = false
)
@TypeConverters(DateConverter.class)

public abstract class Database extends RoomDatabase {

    private static Database INSTANCE;
    private static final String DATABASE_NAME = "journal_app_db";

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), Database.class, DATABASE_NAME)
                            .build();
        }
        return INSTANCE;
    }

    public abstract JournalDao journalDao();

}
