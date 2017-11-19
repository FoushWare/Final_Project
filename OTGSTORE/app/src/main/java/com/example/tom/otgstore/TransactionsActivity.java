package com.example.tom.otgstore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.tom.otgstore.Adapter.TransactionsAdapter;
import com.example.tom.otgstore.models.Message;
import com.google.firebase.iid.FirebaseInstanceId;

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
    private static final String TAG =TransactionsActivity.class.getSimpleName();
    RecyclerView recyclerView;

    private TransactionsAdapter madapter;
    private List<com.example.tom.otgstore.models.Message>messages=new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        //get the token from FCM
        String token=FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onCreate: Token = "+token);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        //test the arraylist
        Message message=new Message();
        message.setContent("test");
        message.setName("test");
        message.setPrice("test");
        message.setQuantity("test");
        messages.add(message);

        Message message3=new Message();
        message.setContent("test2");
        message.setName("test2");
        message.setPrice("test2");
        message.setQuantity("test2");
        messages.add(message3);





        Log.d(TAG, "onCreate: "+messages.size());



        LinearLayoutManager layoutManager=new LinearLayoutManager(TransactionsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        madapter=new TransactionsAdapter(TransactionsActivity.this,messages);
        recyclerView.setAdapter(madapter);
        // scroll to bottom of screen








    }//End of onCreate()

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageReciver,new IntentFilter("UpdateTransactionActivity"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageReciver);

    }

    //BroadcastReceiver to receive the message if the user is in the foreground
    private BroadcastReceiver messageReciver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message message=intent.getParcelableExtra("msg");
            if (message !=null){
                messages.add(message);
                madapter.notifyItemInserted(messages.size() -1);
                recyclerView.scrollToPosition(messages.size()-1);
            }
        }
    };








}
