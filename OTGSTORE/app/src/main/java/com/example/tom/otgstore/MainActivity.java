package com.example.tom.otgstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tom.otgstore.Adapter.OTGItems;
import com.example.tom.otgstore.Adapter.OTGItemsAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 111;
    public static OTGItemsAdapter mOTGItemAdapter;

    //Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mItemsDatabaseReference;
    private DatabaseReference mUsersDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;


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
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        //get Reference to the database [node] called ( items ) for the items and one for the users
        mItemsDatabaseReference = mFirebaseDatabase.getReference().child("items"); //this is like table items
        //get Reference to the database [node] called ( users ) for the users of the item
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("users"); //this is like table items


        //the listener of the firebase to launch FirebaseUi singing if the user not logged in
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(MainActivity.this, "You're now signed in. Welcome to OTG Store.", Toast.LENGTH_SHORT).show();
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    //User is signed out
                    onSignedOutCleanup();
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

    private void onSignedOutCleanup() {
        if(mOTGItemAdapter != null){ mOTGItemAdapter.clear();}
        detachDatabaseListener();

    }

    private void detachDatabaseListener() {
        if (mChildEventListener != null) {
            mItemsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void onSignedInInitialize(String displayName) {
        attachDatabaseReadListener();
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //when child added to item node in the firebase
                    //get the item content in the form specified in the OTGItems class [name,quantity,price,photoUrl]
                    OTGItems otgItems = dataSnapshot.getValue(OTGItems.class);
                    mOTGItemAdapter.add(otgItems);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };

        }
    }

    private void attachDatabaseReadListener() {
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
                //FirebaseAuth.getInstance().signOut();
                //finish();
                break;

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
/*
    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        //clean the adapter
        mOTGItemAdapter.clear();

    }
*/

}
