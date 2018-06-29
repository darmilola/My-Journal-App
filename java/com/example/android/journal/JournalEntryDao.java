
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

import android.app.Notification;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by ALEXANDRE on 6/26/2018.
 */
@Dao
public interface JournalEntryDao {

    @Query("SELECT * FROM JournalEntry")
    List<JournalEntry> getAll();

    @Query("DELETE FROM JournalEntry")
    void delete();

    //@Query("SELECT * FROM JournalEntry WHERE name LIKE :name LIMIT 1")
    //JournalEntry findByName(String name);

    @Update
    void update(JournalEntry message);

    @Delete
    void delete(JournalEntry message);

    @Insert
    void insertentry(JournalEntry Message);
}
