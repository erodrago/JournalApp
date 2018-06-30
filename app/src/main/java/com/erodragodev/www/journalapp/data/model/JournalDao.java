package com.erodragodev.www.journalapp.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface JournalDao {
    @Query("SELECT * FROM journal")
    LiveData<List<MyJournal>> loadAllTasks();

    @Insert
    void insertJournal(MyJournal journalEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateJournal(MyJournal journalEntry);

    @Delete
    void deleteJournal(MyJournal journalEntry);

    @Query("SELECT * FROM journal WHERE id = :id")
    LiveData<MyJournal> loadTaskById(int id);


}
