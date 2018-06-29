
/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.android.journal;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ALEXANDRE on 6/26/2018.
 */

public class JournalEntryAdapter extends RecyclerView.Adapter<JournalEntryAdapter.JournalEntryViewHolder> {

    private JournalListPresenter presenter;
    Context context;

    public List<JournalEntry> JournalEntryList;
    private static ListItemCIickListener mOnClickListener;


    public interface ListItemCIickListener {
        void onListItemClick(int clickedItemIndex,View view);


    }

    public JournalEntryAdapter(JournalListPresenter journalListPresenter) {

        this.presenter = journalListPresenter;

    }


    public class JournalEntryViewHolder extends RecyclerView.ViewHolder implements JournalEntryRowView,View.OnClickListener {

        public TextView EntryTitle;
        public TextView Datetextview;



        public JournalEntryViewHolder(View view) {
            super(view);
            EntryTitle = (TextView) view.findViewById(R.id.entrydescription);
            Datetextview= (TextView) view.findViewById(R.id.datetextview);
           view.setOnClickListener(this);
            context = view.getContext();


        }

/*        public void Bind(final JournalEntry entry, final ListItemCIickListener listener) {


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onListItemClick(getAdapterPosition());
                }
            });
        }*/


        @Override
        public void setTitle(String message) {
            EntryTitle.setText(message);
        }

        @Override
        public void setDate(String date) {
            Datetextview.setText(date);
        }


        @Override
        public void onClick(View v) {

         mOnClickListener.onListItemClick(getAdapterPosition(),v);
        }


     /*   @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
            Log.e("clicked", "onClick: " );



        }*/
    }

    public void setOnItemClickListener(ListItemCIickListener listener){
        JournalEntryAdapter.mOnClickListener=listener;
    }

    @Override
    public JournalEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entryrecyclerview, parent, false);

        return new JournalEntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JournalEntryViewHolder holder, int position) {

       //holder.Bind(JournalEntryList.get(position),mOnClickListener);
        presenter.onBindJournalEntryRowViewAtPosition(position,holder);



       // JournalEntry entry= JournalEntryList.get(position);

        //holder.EntryDescription.setText(entry.getMessage());
    }

    @Override
    public int getItemCount() {
        return presenter.getJournalEntryRowcount();
    }



}
