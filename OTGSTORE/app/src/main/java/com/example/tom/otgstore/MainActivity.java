package com.example.tom.otgstore;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Arrays;

import com.flipboard.bottomsheet.BottomSheetLayout;

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

    BottomSheetLayout bottomSheet;
    SurfaceView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        Boolean isLogin = prefs.getBoolean("isLogin", false);
        if (!isLogin) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
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



    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();

    }

}
