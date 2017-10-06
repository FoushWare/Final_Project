package com.example.tom.otgstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
/**
 *In this class there will be
 *
 *1-login to the App using firebaseUi [to Authonticate acess to the database and our app]
 *  we want if the user first sign up to the app redirect him/her to the sign up activity to
 *  generate QR code for him/her and if he signed in Rediredt to the MainActivty which will have QR floating button
 *  of him/her
 *2-There are  buttons to redirect to our Acivities [Balance,History,Profile,Transactions]
 *3-make the database of Firebase with two nodes[tables] (users,items)
 *4-make listener to listen to the database if there is new child added and detach this listener on pause
 *5-performing signOut of the app  but there is a bug with sign in again it close the app but after click on it
 *  there is no error
 * */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 111;

    //Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the views of the MainActivity
        Button ProfileButton = (Button) findViewById(R.id.buttonProfile);
        Button BalanceButton = (Button) findViewById(R.id.buttonBalance);
        Button HistoryButton = (Button) findViewById(R.id.buttonHistory);
        Button SignOutButton = (Button) findViewById(R.id.buttonSignOut);
        Button ShoppingButton = (Button) findViewById(R.id.buttonShopping);
        //set listeners for the buttons
        ProfileButton.setOnClickListener((View.OnClickListener) this);
        BalanceButton.setOnClickListener((View.OnClickListener) this);
        HistoryButton.setOnClickListener((View.OnClickListener) this);
        SignOutButton.setOnClickListener((View.OnClickListener) this);
        ShoppingButton.setOnClickListener((View.OnClickListener) this);

        //Initialize Firebase components
        mFirebaseAuth = FirebaseAuth.getInstance();

        //the listener of the firebase to launch FirebaseUi singing if the user not logged in
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            /**
             * The sign in operation using FirebaseUi
             * */

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(MainActivity.this, "You're now signed in. Welcome to OTG Store.", Toast.LENGTH_SHORT).show();
                } else {
                    //User is signed out
                    //onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);

                }


            }
        };

    }//End of onCreate

    //Implement onActivityResult for cancelled FirebaseUI Auth
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    //implement the onClick method here
    public void onClick(View v) {

        //perform action on click
        switch (v.getId()) {
            case R.id.buttonProfile:
                Intent profile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;
            case R.id.buttonHistory:
                Intent history = new Intent(MainActivity.this, HistoryAcivity.class);
                startActivity(history);
                break;
            case R.id.buttonBalance:
                Intent balance = new Intent(MainActivity.this, BalanceActivity.class);
                startActivity(balance);
                break;
            case R.id.buttonShopping:
                Intent shopping = new Intent(MainActivity.this, TransactionsActivity.class);
                startActivity(shopping);
                break;
            case R.id.buttonSignOut:
                //make sign out not implemented yet
                //there is a problem the activty closed after sign in i'll figure it later
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

}
