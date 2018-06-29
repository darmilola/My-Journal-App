
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

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by ALEXANDRE on 6/26/2018.
 */


@Entity
public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "Message")
    private String Message;

    @ColumnInfo(name = "Title")
    private String Title;

    @ColumnInfo(name = "dateUpdated")
    private Date dateUpdated;

    public String getMessage(){

        return this.Message;
    }

    public void  setMessage(String Value){

        this.Message= Value;
    }

    public void setUid(int uid){
        this.uid=uid;
    }
    public int getUid(){
        return uid;
    }

    public void setTitle(String title){
        this.Title=title;
    }
    public String getTitle(){
        return this.Title;
    }

   public void setDateUpdated(Date value){
       this.dateUpdated=value;
   }

    public Date getDateUpdated(){

        return this.dateUpdated;
    }

}
