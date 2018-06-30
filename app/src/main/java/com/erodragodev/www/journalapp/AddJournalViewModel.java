package com.erodragodev.www.journalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.erodragodev.www.journalapp.data.Database;
import com.erodragodev.www.journalapp.data.model.MyJournal;



public class AddJournalViewModel extends ViewModel {

    private LiveData<MyJournal> journal;


    public AddJournalViewModel(Database database, int journalId) {
        journal = database.journalDao().loadTaskById(journalId);
    }

    public LiveData<MyJournal> getTask() {
        return journal;
    }
}
