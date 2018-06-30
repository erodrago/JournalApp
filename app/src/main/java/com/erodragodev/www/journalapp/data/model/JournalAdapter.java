package com.erodragodev.www.journalapp.data.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erodragodev.www.journalapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {
     private static final String DATE_FORMAT = "EEEE";


    final private ItemClickListener mItemClickListener;
    private List<MyJournal> mJournalEntries;
    private Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public JournalAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }


    @Override
    public JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.journal_list_layout, parent, false);

        return new JournalViewHolder(view);
    }


    //binds data to views

    @Override
    public void onBindViewHolder(JournalViewHolder holder, int position) {

        MyJournal myJournal = mJournalEntries.get(position);
        String mood = myJournal.getMood();
        String title = myJournal.getJournalTitle();
        String details = myJournal.getJournalDesc();
        String date = dateFormat.format(myJournal.getDate());
        String[] parts =date.split("/");

        Date d= new Date();
        String dayOfTheWeek =dateFormat.format(d);


        //Set values
        holder.dayNo.setText(parts[0]);
        holder.day.setText(dayOfTheWeek);
        Resources res=Resources.getSystem();
        holder.moodImg.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_launcher_foreground));
        holder.dayTime.setText(dayOfTheWeek);
        holder.monthYr.setText(dayOfTheWeek);
        holder.journalDesc.setText(dayOfTheWeek);



    }



    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mJournalEntries == null) {
            return 0;
        }
        return mJournalEntries.size();
    }

    public List<MyJournal> getTasks() {
        return mJournalEntries;
    }

    /**
     * When data changes, this method updates the list of JournalEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<MyJournal> journalEntries) {
        mJournalEntries = journalEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView dayNo,day,monthYr,dayTime,journalDesc;
        ImageView moodImg;


        //View Holder Constructor
        public JournalViewHolder(View itemView) {
            super(itemView);



            dayNo = itemView.findViewById(R.id.tvDayno);
            day = itemView.findViewById(R.id.tvDay);
            monthYr = itemView.findViewById(R.id.tvMonthYr);
            dayTime = itemView.findViewById(R.id.tvTime);
            journalDesc = itemView.findViewById(R.id.tvDetails);
            moodImg = itemView.findViewById(R.id.imgMood);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mJournalEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
