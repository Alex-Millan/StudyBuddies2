package com.example.alex.studybuddies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "WHYFAILTHO";
    private String HAGS = "ALEXISLAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent2 = new Intent(this, MapsActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent2);
        authenticating();



    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            //    mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void onSignIn(View v) {

        EditText userEmail  = (EditText) findViewById(R.id.userEmail);
        EditText userPassword = (EditText) findViewById(R.id.userPassword);

        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (email.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            return;
        }        if (password.matches("")) {
            Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        //loggedIn();

                        // ...
                    }
                });
        // If sign in fails, display a message to the user. If sign in succeeds
        // the auth state listener will be notified and logic to handle the
        // signed in user can be handled in the listener.
        authenticating();
    }

    public void authenticating() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //loggedIn();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }


    public void onSignUp(View V) {
        Intent intent2 = new Intent(this, SignUpActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent2);
    }

    public void onUploadTest(View V) {
        //Intent intent2 = new Intent(this, UploadData.class);
        //intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent2);
    }

    private void loggedIn() {
        //Intent intent = new Intent(this, UploadData.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
    }

}
