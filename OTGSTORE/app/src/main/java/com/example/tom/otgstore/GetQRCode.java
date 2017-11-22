package com.example.tom.otgstore;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetQRCode extends AppCompatActivity {

    String token="";
    String firebaseToken="";

    ImageView QRImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_qrcode);

        QRImage = (ImageView) findViewById(R.id.QRImage);
        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        token = prefs.getString("token", null);
        firebaseToken =  prefs.getString("firebase_token", null);

        new GetQR().execute(firebaseToken);

    }

    private class GetQR extends AsyncTask<String, Void, Void> {

        String json="", error="", message="";
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(GetQRCode.this); // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... arg) {
            // TODO Auto-generated method stub
            String firebaseToken = arg[0];


            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("firebase_token", "asdasdasd"));

            ServiceHandler serviceClient = new ServiceHandler();

            json = serviceClient.makeServiceCall("http://www.mommmmom.esy.es/api/v1/orders/registration?token="+token,
                    ServiceHandler.POST, params);



            Log.d("CreatePredictionRequest", "> " + json);


            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(GetQRCode.this, json, Toast.LENGTH_SHORT).show();

            dialog.dismiss();

            Glide.with(GetQRCode.this).load(json).into(QRImage);


        }
    }
}
