
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
import android.app.VoiceInteractor;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ImageView newentry;
    ImageView bottommenu;
    Intent mintent;
    JournalDataBase journalDataBase;
    String t;
    public RecyclerView recyclerView;
    public RelativeLayout relativeLayout;
    public RecyclerView.Adapter recyclerViewAdapter;
    public RecyclerView.LayoutManager recylerViewLayoutManager;
    List<JournalEntry>  entries;
    JournalListPresenter journalListPresenter;
    JournalEntryAdapter journalEntryAdapter;


    // Firebase Auth Object.
    public FirebaseAuth firebaseAuth;

    // Google API Client object.
    public GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

         journalDataBase = Room.databaseBuilder(getApplicationContext(), JournalDataBase.class, "Journaldatabase").build();




       new Thread(new Runnable() {
           @Override
           public void run() {




               entries= journalDataBase.JournalEntryDaoAccess().getAll();


               journalListPresenter = new JournalListPresenter(entries);




               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       journalEntryAdapter= new JournalEntryAdapter(journalListPresenter);
                       journalEntryAdapter.notifyDataSetChanged();

                       recyclerView.setAdapter(journalEntryAdapter);


                       journalEntryAdapter.setOnItemClickListener(new JournalEntryAdapter.ListItemCIickListener() {
                           @Override
                           public void onListItemClick(int clickedItemIndex,View view) {

                               Log.e("clicked", "onListItemClick: ");
                               JournalEntry journalEntry= entries.get(clickedItemIndex);
                               String message=journalEntry.getMessage();
                               String title=journalEntry.getTitle();
                               int uid= journalEntry.getUid();
                               Intent intent= new Intent(MainActivity.this,Readinpage.class);

                               intent.putExtra("message",message);
                               intent.putExtra("uid",uid);
                               intent.putExtra("title",title);

                               startActivity(intent);




                           }
                       });




                   }
               });

           }
       }).start();



        recyclerView = (RecyclerView) findViewById(R.id.journalEntryrecyclerview);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager LayoutManager = (LinearLayoutManager) mLayoutManager;
        recyclerView.setLayoutManager(LayoutManager);


      //  recyclerView.setHasFixedSize(true);


       // List<Product> products = App.get().getDB().productDao().getAll();






        mintent= new Intent(MainActivity.this, LoginPage.class);
        InitializeView();

  firebaseAuth=FirebaseAuth.getInstance();
        if (bottommenu != null) {

            bottommenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showMenu(v);

                }
            });

        }


        if (newentry != null) {
            newentry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), CreateNewEntry.class);
                    startActivity(i);
                }
            });
        }

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

    }



    private void InitializeView(){

        newentry = (ImageView) findViewById(R.id.newentry);
        bottommenu= (ImageView) findViewById(R.id.menu);

    }

    private void showMenu(View v){

        PopupMenu popupMenu= new PopupMenu(this,v);
        MenuInflater inflater= popupMenu.getMenuInflater();
        inflater.inflate(R.menu.bottommenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.deleteall:
                AlertDialog.Builder alertDialog= new AlertDialog.Builder(MainActivity.this);
                alertDialog.setMessage("Are you sure you want to delete all entry");
                alertDialog.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       new Thread(new Runnable() {
                           @Override
                           public void run() {
                               journalDataBase.JournalEntryDaoAccess().delete();


                           }
                       });

                    }
                });

                alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
                alertDialog.show();
                journalEntryAdapter.notifyDataSetChanged();
                return true;

            case R.id.logout:
              UserSignOut();


                return true;
        }

        return false;

    }



    public void UserSignOut(){

        // Sing Out the User.
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback() {
                    @Override
                    public void onResult(@NonNull Result result) {

                        startActivity(mintent);



                        Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_LONG).show();

                    }


                });


    }

    @Override
    public void onBackPressed(){

    }


}
