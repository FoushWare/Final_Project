package com.example.tom.otgstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.tom.otgstore.Adapter.OTGItemsAdapter;
import com.example.tom.otgstore.Adapter.OTGItems;

import java.util.ArrayList;
import java.util.List;
/**
 *There we will show the user everything he/she buy in the realtime
 * i made adapter for this
 *
 * */

public class TransactionsActivity extends AppCompatActivity {

    private ListView mItemsListView;
    public OTGItemsAdapter OTGItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        //get the listView
        mItemsListView=(ListView)findViewById(R.id.list);

        //initialize items listView and its adapter
        List<OTGItems> otGitemses= new ArrayList<>();
        OTGItemsAdapter= new OTGItemsAdapter(this,R.layout.list_item,otGitemses);
        mItemsListView.setAdapter(OTGItemsAdapter);

    }
}
