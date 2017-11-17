package com.example.tom.otgstore;

import android.content.Intent;
import android.content.SharedPreferences;
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

 *5-performing signOut of the app  but there is a bug with sign in again it close the app but after click on it
 *  there is no error
 * */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        Boolean isLogin = prefs.getBoolean("isLogin", false);
        if (!isLogin ) {
           Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }else {

            setContentView(R.layout.activity_main);

            /*
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
*/

        }
    }




    //implement the onClick method here
    public void onClick(View v) {
/*
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
                SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.putBoolean("isLogin", false);
                editor.apply();

                break;
            default:
                break;

        }
        */
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();

    }

}
