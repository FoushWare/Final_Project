package com.example.tom.otgstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.tom.otgstore.Adapter.OTGItems;
import com.example.tom.otgstore.Adapter.OTGItemsAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
/**
 *There we will show the user everything he/she buy in the realtime
 * i made adapter for this
 *
 * */

public class TransactionsActivity extends AppCompatActivity {

    private ListView mItemsListView;
    private OTGItemsAdapter mOTGItemsAdapter;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mItemsDatabaseReference;
    private DatabaseReference mUsersDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        //Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        //get Reference to the database [node] called ( items ) for the items and one for the users
        mItemsDatabaseReference = mFirebaseDatabase.getReference().child("items"); //this is like table items
        //get Reference to the database [node] called ( users ) for the users of the item
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("users"); //this is like table items
        //get the listView
        mItemsListView=(ListView)findViewById(R.id.list);

        //initialize items listView and its adapter
        List<OTGItems> otGitemses= new ArrayList<>();
        mOTGItemsAdapter= new OTGItemsAdapter(this,R.layout.list_item,otGitemses);
        mItemsListView.setAdapter(mOTGItemsAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //attach listener to the database
        onSignedInInitialize();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //detach listener from the database
        onSignedOutCleanup();

    }
    private void onSignedOutCleanup() {
        if(mOTGItemsAdapter != null){ mOTGItemsAdapter.clear();}
        detachDatabaseListener();

    }

    private void detachDatabaseListener() {
        if (mChildEventListener != null) {
            mItemsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void onSignedInInitialize() {
        attachDatabaseReadListener();

    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //when child added to item node in the firebase
                    //get the item content in the form specified in the OTGItems class [name,quantity,price,photoUrl]
                    OTGItems otgItems = dataSnapshot.getValue(OTGItems.class);
                    mOTGItemsAdapter.add(otgItems);

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
            mItemsDatabaseReference.addChildEventListener(mChildEventListener);

        }
    }
}
