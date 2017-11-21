package com.example.tom.otgstore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 *in this Activity we will sent the card number to Moustafa and get respond if's [ok] OR [not]
 *
 * if it's [ok] add this balance to the user account
 * if [not] tell the user ,the card number is wrong
 * */
public class BalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
    }
}
