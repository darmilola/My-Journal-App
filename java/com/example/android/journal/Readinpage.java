
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

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Readinpage extends Activity {
    TextView readingText,titleview;
    String message,title;
    ImageView edit,delete;
    int uid;
     JournalDataBase journalDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_readingpage);

        initialzeView();
         initialzeView();



        message=getIntent().getStringExtra("message");
        uid= getIntent().getIntExtra("uid",uid);
        title=getIntent().getStringExtra("title");
        if(message!=null){
            readingText.setText(message);
        }

        if(title!=null){
            titleview.setText(title);
        }


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text= readingText.getText().toString();
                Intent intent= new Intent(Readinpage.this,CreateNewEntry.class);
                intent.putExtra("text",text);
                intent.putExtra("updating","updating");
                intent.putExtra("uid",uid);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder alertDialog= new AlertDialog.Builder(Readinpage.this);
                alertDialog.setMessage("Are you sure you want to delete entry");
                alertDialog.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String message = readingText.getText().toString();
                        JournalEntry journalEntry = new JournalEntry();
                        journalEntry.setMessage(message);
                        journalEntry.setTitle(title);
                        journalEntry.setUid(uid);
                        journalDataBase.JournalEntryDaoAccess().delete(journalEntry);


                        Intent intent = new Intent(Readinpage.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Entry Deleted", Toast.LENGTH_SHORT).show();


                    }


                });

                alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();


            }
        });

    }



    public void initializedatabase(){
        journalDataBase = Room.databaseBuilder(getApplicationContext(), JournalDataBase.class, "Journaldatabase").allowMainThreadQueries().build();

    }

    public void initialzeView(){

        edit= (ImageView) findViewById(R.id.editentry);
        delete= (ImageView) findViewById(R.id.deleteentry);
        readingText= (TextView) findViewById(R.id.readingtext);
        titleview= (TextView) findViewById(R.id.readinpageentrytitle);
    }
}
