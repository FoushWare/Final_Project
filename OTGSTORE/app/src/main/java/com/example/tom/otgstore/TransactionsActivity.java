package com.example.tom.otgstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tom.otgstore.Adapter.OTGItems;
import com.example.tom.otgstore.Adapter.OTGItemsAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * There we will show the user everything he/she buy in the realtime
 * i made adapter for this
 * There in this class
 * 1-make the database of Firebase with two nodes[tables] (users,items)
 * 2-make listener to listen to the database if there is new child added and detach this listener on pause
 */

public class TransactionsActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 50;
    private ListView mItemsListView;
    private OTGItemsAdapter mOTGItemsAdapter;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mItemsDatabaseReference;
    private DatabaseReference mUsersDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mItemsPhotos;
    private Uri mDownloadUrl;

    /**
     * Views  of the photoPicker EditTexts for [  name price quantity ] for test the DB
     */
    private EditText mNameEditText;
    private EditText mQuantityEditText;
    private EditText mPriceEditText;
    private Button mSendButton;
    private ImageButton mPhotoPicker;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            //get the uri of the selected photo
            Uri selectedImageUri = data.getData();
            // Get a reference to store file at items_photos/<FILENAME>
            StorageReference photoRef = mItemsPhotos.child(selectedImageUri.getLastPathSegment());
            // Upload file to Firebase Storage
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            mDownloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText(TransactionsActivity.this,"Photo uploaded ",Toast.LENGTH_LONG);
                        }
                    });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        /**get the views of the photoPicker EditTexts for [  name price quantity ] for test the DB
         * */
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mQuantityEditText = (EditText) findViewById(R.id.quantityEditText);
        mPriceEditText = (EditText) findViewById(R.id.priceEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);
        mPhotoPicker = (ImageButton) findViewById(R.id.photoPickerButton);


        //Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        //get Reference to the database [node] called ( items ) for the items and one for the users
        mItemsDatabaseReference = mFirebaseDatabase.getReference().child("items"); //this is like table items
        //get Reference to the database [node] called ( users ) for the users of the item
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("users"); //this is like table items

        //get Reference for the folder containing the items photo in the storage of the firebase
        mItemsPhotos = mFirebaseStorage.getReference().child("items_photos");

        //get the listView
        mItemsListView = (ListView) findViewById(R.id.list);

        //initialize items listView and its adapter
        List<OTGItems> otGitemses = new ArrayList<>();
        mOTGItemsAdapter = new OTGItemsAdapter(this, R.layout.list_item, otGitemses);
        mItemsListView.setAdapter(mOTGItemsAdapter);

        /**
         *Test adding items to the firebase DB
         *using photoPicker  and EditTexts for [ name price quantity ]
         * */



        //make listener for the photo picker [ImageButton] this will open the gallery to select photo form it
        mPhotoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

       /** add listener to the send button  to write items to DB to test the DB
        * */
       mSendButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               OTGItems item=new OTGItems(mNameEditText.getText().toString(),mQuantityEditText.getText().toString(),mPriceEditText.getText().toString(),mDownloadUrl.toString());
               mItemsDatabaseReference.push().setValue(item);
           }
       });



    }//End of onCreate()

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
        if (mOTGItemsAdapter != null) {
            mOTGItemsAdapter.clear();
        }
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
