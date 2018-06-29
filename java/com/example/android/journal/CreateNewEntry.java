/*
 * Copyright 2018 The Android Open Source Project
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

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.Date;

public class CreateNewEntry extends AppCompatActivity{

    ImageView SaveEntryCreation;
    ImageView CancelEntryCreation;
    EditText  NoteEditText,titleview;
    JournalDataBase journalDataBase;
    JournalEntry journalEntry;
    String EditString,title,updatingmode;
    int uid;
    EditText noteTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_new_entry);
        EditString=getIntent().getStringExtra("text");
        uid=getIntent().getIntExtra("uid",uid);
        title=getIntent().getStringExtra("title");
        updatingmode=getIntent().getStringExtra("updating");
        journalDataBase = Room.databaseBuilder(getApplicationContext(), JournalDataBase.class, "Journaldatabase").allowMainThreadQueries().build();


        initialiazeview();

        if(updatingmode!=null){
         NoteEditText.setText(EditString);
         titleview.setText(title);


        }
        if (CancelEntryCreation != null) {

            CancelEntryCreation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }

        if(SaveEntryCreation!=null){

            SaveEntryCreation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    if(updatingmode!=null){


                        String entry= NoteEditText.getText().toString();
                        String title=titleview.getText().toString();
                        Date date= new Date();
                        journalEntry = new JournalEntry();
                        journalEntry.setMessage(entry);
                        journalEntry.setTitle(title);
                        journalEntry.setDateUpdated(date);
                        journalEntry.setUid(uid);

                       journalDataBase.JournalEntryDaoAccess().update(journalEntry);
                        Toast.makeText(CreateNewEntry.this,"Entry Updated",Toast.LENGTH_SHORT).show();
                    }


                            if(updatingmode==null){


                                Log.e("saving entry", "saving entry ");

                                String entry= NoteEditText.getText().toString();
                                String title=titleview.getText().toString();
                                Date date= new Date();
                                journalEntry = new JournalEntry();
                                journalEntry.setMessage(entry);
                                journalEntry.setTitle(title);
                                journalEntry.setDateUpdated(date);

                                Log.e("i saved it", " i saved it " );

                                journalDataBase.JournalEntryDaoAccess().insertentry(journalEntry);
                                Toast.makeText(CreateNewEntry.this,"Entry created",Toast.LENGTH_SHORT).show();



                            }
                     Intent intent= new Intent(CreateNewEntry.this,MainActivity.class);
                      startActivity(intent);

                        }


            });
        }



    }



    private void initialiazeview(){

        SaveEntryCreation= (ImageView) findViewById(R.id.saveentry);
        CancelEntryCreation= (ImageView) findViewById(R.id.cancelentrycreation);
        NoteEditText= (EditText) findViewById(R.id.createnoteedittext);
         titleview= (EditText) findViewById(R.id.createnotetitleedittext);
    }
}
