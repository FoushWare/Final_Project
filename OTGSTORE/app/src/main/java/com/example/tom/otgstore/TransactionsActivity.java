package com.example.tom.otgstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.tom.otgstore.Adapter.OTGItemsAdapter;
import com.example.tom.otgstore.Adapter.OTGitems;

import java.util.ArrayList;
import java.util.List;

public class TransactionsActivity extends AppCompatActivity {

    private ListView mItemsListView;
    private OTGItemsAdapter mOTGItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        //get the listView
        mItemsListView=(ListView)findViewById(R.id.list);

        //initialize items listView and its adapter
        List<OTGitems> otGitemses= new ArrayList<>();
        mOTGItemsAdapter= new OTGItemsAdapter(this,R.layout.list_item,otGitemses);
        mItemsListView.setAdapter(mOTGItemsAdapter);

    }
}
