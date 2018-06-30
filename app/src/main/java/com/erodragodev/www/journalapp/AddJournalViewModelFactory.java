package com.erodragodev.www.journalapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.erodragodev.www.journalapp.data.Database;

public class AddJournalViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Database mDb;
    private final int mJournalId;

    public AddJournalViewModelFactory(Database database, int journalId) {
        mDb = database;
        mJournalId = journalId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddJournalViewModel(mDb, mJournalId);
    }
}
