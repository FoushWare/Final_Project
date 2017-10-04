package com.example.tom.otgstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the views of the MainActivity
        Button ProfileButton=(Button)findViewById(R.id.buttonProfile);
        Button BalanceButton=(Button)findViewById(R.id.buttonBalance);
        Button HistoryButton=(Button)findViewById(R.id.buttonHistory);
        Button SignOutButton=(Button)findViewById(R.id.buttonSignOut);
        Button ShoppingButton=(Button)findViewById(R.id.buttonShopping);


        //set listeners for the buttons
        ProfileButton.setOnClickListener((View.OnClickListener) this);
        BalanceButton.setOnClickListener((View.OnClickListener) this);
        HistoryButton.setOnClickListener((View.OnClickListener) this);
        SignOutButton.setOnClickListener((View.OnClickListener) this);
        ShoppingButton.setOnClickListener((View.OnClickListener) this);

    //make intents to map every button to the right Activity






    }


//implement the onClick method here
    public void onClick(View v) {

       //perform action on click
        switch (v.getId()){
            case R.id.buttonProfile:
              Intent profile= new Intent(MainActivity.this,ProfileActivity.class);
             startActivity(profile);
             break;
            case R.id.buttonHistory:
                Intent history= new Intent(MainActivity.this,HistoryAcivity.class);
                startActivity(history);
                break;
            case R.id.buttonBalance:
                Intent balance= new Intent(MainActivity.this,BalanceActivity.class);
                startActivity(balance);
                break;
            case R.id.buttonShopping:
                Intent shopping= new Intent(MainActivity.this,TransactionsActivity.class);
                startActivity(shopping);
                break;
            case R.id.buttonSignOut:
                //make sign out not implemented yet
                break;

        }






    }

}
