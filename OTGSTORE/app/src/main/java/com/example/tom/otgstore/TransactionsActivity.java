package com.example.tom.otgstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tom.otgstore.Adapter.TransactionsAdapter;

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
    RecyclerView recyclerView;

    private TransactionsAdapter madapter;
    private List<Message> messageList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);




















    }//End of onCreate()

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }








}
