package com.erodragodev.www.journalapp.data.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "journal")
public class MyJournal {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String mood;
    private String journalTitle;
    private String journalDesc;


    @Ignore
    public MyJournal(Date date, String mood, String journalTitle, String journalDesc) {
        this.date = date;
        this.mood = mood;
        this.journalTitle = journalTitle;
        this.journalDesc = journalDesc;
    }

    public MyJournal(int id, Date date, String mood, String journalTitle, String journalDesc) {
        this.id = id;
        this.date = date;
        this.mood = mood;
        this.journalTitle = journalTitle;
        this.journalDesc = journalDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    public String getJournalDesc() {
        return journalDesc;
    }

    public void setJournalDesc(String journalDesc) {
        this.journalDesc = journalDesc;
    }
}
