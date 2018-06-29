
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
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends AppCompatActivity {

    // Request sing in code. Could be anything as you required.
    public static final int RequestSignInCode = 7;

    // Firebase Auth Object.
    public FirebaseAuth firebaseAuth;

    // Google API Client object.
    public GoogleApiClient googleApiClient;



    Button SigninButton;
    // Google Sign In button .
   Button gSignOutButton;
   ProgressBar mProgressbar;



    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);
         mProgressbar= (ProgressBar) findViewById(R.id.mprogressBar);
        mProgressbar.setVisibility(View.INVISIBLE);
         SigninButton  = (Button) findViewById(R.id.googlebutton);
        intent = new Intent(getApplicationContext(), MainActivity.class);
        firebaseAuth = FirebaseAuth.getInstance();


//creating and configuring google sign checker
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
if(SigninButton== null){
    Log.e("NULL", "BUTTON" );
}
        if (SigninButton != null) {
            SigninButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    UserSignIn();


                }


            });

        }

    }









        public void UserSignIn(){



            // Passing Google Api Client into Intent.
            Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

            startActivityForResult(AuthIntent, RequestSignInCode);

        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestSignInCode){

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mProgressbar.setVisibility(View.VISIBLE);

            if (googleSignInResult.isSuccess()){

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                FirebaseUserAuth(googleSignInAccount);






            }

        }
    }



    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);



        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {

                        if (AuthResultTask.isSuccessful()){



                            startActivity(intent);



                        }else {
                            mProgressbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}

