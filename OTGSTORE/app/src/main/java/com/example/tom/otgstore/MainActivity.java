package com.example.tom.otgstore;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    BottomSheetLayout bottomSheet;
    SurfaceView cameraView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //shared prefrence to get firebaseToken
        SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
        editor.putString("firebase_token", null);
        editor.apply();

        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        Boolean isLogin = prefs.getBoolean("isLogin", false);
        if (!isLogin) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
            //get the firebaseToken of the user after know he/she isLogin
            String firebaseToken=FirebaseInstanceId.getInstance().getToken();
            editor.putString("firebase_token",firebaseToken);
            editor.apply();

        }

        bottomSheet = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        LinearLayout scanQR = (LinearLayout) findViewById(R.id.buttonSearch);
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet.showWithSheetView(LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_view, bottomSheet, false));
            }
        });


        cameraView = (SurfaceView) findViewById(R.id.camera_view);



    }

    //Implement onActivityResult for cancelled FirebaseUI Auth
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


    }


    //implement the onClick method here
    public void onClick(View v) {

        Intent intent = new Intent();
        String transitionName = "trans";

        //perform action on click
        switch (v.getId()) {
            case R.id.buttonProfile:
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                break;
            case R.id.buttonHistory:
                intent = new Intent(MainActivity.this, HistoryAcivity.class);
                break;
            case R.id.buttonBalance:
                intent = new Intent(MainActivity.this, BalanceActivity.class);
                break;
            case R.id.buttonShopping:
                intent = new Intent(MainActivity.this, TransactionsActivity.class);
                break;
            case R.id.buttonSignOut:
                SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.putBoolean("isLogin", false);
                editor.apply();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                break;

            case R.id.QRBtn:
                intent = new Intent(MainActivity.this, GetQRCode.class);
                break;
            default:
                break;


        }

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, v, transitionName);
            startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            startActivity(intent);
        }


    }

    //This part related to FCM Ui
    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    protected void onPause() {
        super.onPause();
    }


}


