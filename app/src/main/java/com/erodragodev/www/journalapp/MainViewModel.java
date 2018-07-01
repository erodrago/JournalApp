package com.erodragodev.www.journalapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.erodragodev.www.journalapp.data.Database;
import com.erodragodev.www.journalapp.data.model.MyJournal;

import java.util.List;

public class MainViewModel extends AndroidViewModel{


    private Database appDatabase;
    private final LiveData<List<MyJournal>> journalList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        appDatabase = Database.getDatabase(this.getApplication());
        //Initialize User List
        journalList = appDatabase.journalDao().loadAllTasks();
    }

    //Add New Journal
    public void addJournal(MyJournal myJournal) {
        new addAsyncTask(appDatabase).execute(myJournal);
    }

    // Get Journal
    public LiveData<List<MyJournal>> getJournalList() {
        return journalList;
    }

    private static class addAsyncTask extends AsyncTask<MyJournal, Void, Void> {

        private Database db;

        addAsyncTask(Database appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final MyJournal... params) {
            db.journalDao().insertJournal(params[0]);
            return null;
        }

    }
}
