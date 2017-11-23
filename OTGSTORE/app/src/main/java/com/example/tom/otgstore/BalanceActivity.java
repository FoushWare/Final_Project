package com.example.tom.otgstore;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *in this Activity we will sent the card number to Moustafa and get respond if's [ok] OR [not]
 *
 * if it's [ok] add this balance to the user account
 * if [not] tell the user ,the card number is wrong
 * */
public class BalanceActivity extends AppCompatActivity {

    EditText cardNumbertxt;
    private String URL;
    TextView cardCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        setUpWindowAnimations();



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        String token = prefs.getString("token", null);


        URL = "http://www.mommmmom.esy.es/api/v1/users/credits?token="+token;

        cardNumbertxt =(EditText) findViewById(R.id.cardNumbertxt);
        cardCredit=(TextView)findViewById(R.id.currentCredit);

    }

    public void onSubmit(View view){
        String cardNum = cardNumbertxt.getText().toString();
        if(cardNum.equals("")){
            cardNumbertxt.setError("Card number not valid");
        }else{
            new ChargeBalance().execute(cardNum);
        }
    }

    private class ChargeBalance extends AsyncTask<String, Void, Void> {

        String json="", error="", message="",credits="";

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(BalanceActivity.this); // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... arg) {
            // TODO Auto-generated method stub
            String hash = arg[0];



            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("hash", hash));


            ServiceHandler serviceClient = new ServiceHandler();

            json = serviceClient.makeServiceCall(URL,
                    ServiceHandler.POST, params);


            Log.d("CreatePredictionRequest", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    error = jsonObj.getString("error");
                    message = jsonObj.getString("msg");

                    credits=jsonObj.getString("credits");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "JSON data error!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            dialog.dismiss();

            if(error.equals("0")) {
                Toast.makeText(BalanceActivity.this,"Balance charged successfully ", Toast.LENGTH_SHORT).show();

                //assign user currentCredit
                cardCredit.setText(credits);


            }else if(error.equals("1")){
                Toast.makeText(BalanceActivity.this,"Server Error: "+message, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(BalanceActivity.this,"Unknown Error occur, please try again ", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void setUpWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Log.i("ANIM", "slide called");
            Slide slide = new Slide(Gravity.LEFT);
            slide.setDuration(200);
            getWindow().setEnterTransition(slide);
        }

    }
}