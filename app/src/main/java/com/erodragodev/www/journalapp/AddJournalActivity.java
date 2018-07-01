package com.erodragodev.www.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.erodragodev.www.journalapp.data.Database;
import com.erodragodev.www.journalapp.data.model.MyJournal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddJournalActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_JOURNAL_ID = "extraJournalId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_JOURNAL_ID = "instanceTaskId";
    private static final int DEFAULT_TASK_ID = -1;
    Spinner spMood;
    TextView tvDate;
    EditText edJournalTitle,edJournalDetail;
    Button btnPost;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    private int mJournalId = DEFAULT_TASK_ID;

    // Member variable for the Database
    private Database mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDate=(TextView) findViewById(R.id.tvdate);
        spMood=(Spinner)findViewById(R.id.spMood);
        edJournalTitle=(EditText)findViewById(R.id.edJournalTitle);
        edJournalDetail=(EditText)findViewById(R.id.edJournalDetail);
        btnPost=(Button)findViewById(R.id.btnPost);


        Date date =new Date();
        tvDate.setText(dateFormat.format(date));
        String[] mood={"Hows your mood today?","Happy","Sad","Feeling loved","Productive","Annoyed"};

        ArrayAdapter moodAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,mood);
        spMood.setAdapter(moodAdapter);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
        mDb = Database.getDatabase(getApplicationContext());
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_JOURNAL_ID)) {
            mJournalId = savedInstanceState.getInt(INSTANCE_JOURNAL_ID, DEFAULT_TASK_ID);
        }
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_JOURNAL_ID)) {
            btnPost.setText(R.string.update_button);
            if (mJournalId == DEFAULT_TASK_ID) {
                // populate the UI
                mJournalId = intent.getIntExtra(EXTRA_JOURNAL_ID, DEFAULT_TASK_ID);

                AddJournalViewModelFactory factory = new AddJournalViewModelFactory(mDb, mJournalId);
                final AddJournalViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddJournalViewModel.class);


                viewModel.getTask().observe(this, new Observer<MyJournal>() {
                    @Override
                    public void onChanged(@Nullable MyJournal myJournal) {
                        viewModel.getTask().removeObserver(this);
                        populateUI(myJournal);
                    }
                });
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_JOURNAL_ID, mJournalId);
        super.onSaveInstanceState(outState);
    }
    private void populateUI(MyJournal myJournal) {
        if (myJournal == null) {
            return;
        }

        edJournalTitle.setText(myJournal.getJournalTitle());
        edJournalDetail.setText(myJournal.getJournalDesc());

    }
    public void onSaveButtonClicked() {
        String jtitle = edJournalTitle.getText().toString();
        String description = edJournalDetail.getText().toString();
        String mood = spMood.getSelectedItem().toString();


        Date date = new Date();

        final MyJournal journal = new MyJournal(date,mood,jtitle,description);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mJournalId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.journalDao().insertJournal(journal);
                } else {
                    //update task
                    journal.setId(mJournalId);
                    mDb.journalDao().updateJournal(journal);
                }
                finish();
            }
        });


    }
}
