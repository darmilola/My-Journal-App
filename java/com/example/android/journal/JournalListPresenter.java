
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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by ALEXANDRE on 6/26/2018.
 */

public class JournalListPresenter {
    private static final String DATE_FORMAT = "dd/MM/yyy";

    private  List<JournalEntry> JournalEntryList;
    private SimpleDateFormat simpleDateFormat= new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());




    public JournalListPresenter(List<JournalEntry> journalEntryList) {
        JournalEntryList = journalEntryList;
    }

        public void onBindJournalEntryRowViewAtPosition(int position, JournalEntryRowView rowview) {
            JournalEntry mJournalEntry = JournalEntryList.get(position);
            String dateupdated= simpleDateFormat.format(mJournalEntry.getDateUpdated());
            rowview.setTitle(mJournalEntry.getTitle());
            rowview.setDate(dateupdated);
        }

        public int getJournalEntryRowcount(){

            return JournalEntryList.size();
        }




    }

