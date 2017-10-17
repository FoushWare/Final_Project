package com.example.tom.otgstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    /**
     *There we will perform the signUp operation including the vision part [dataSet]
     * name ,username ,photo
     * */

    EditText usertxt, mailtxt, phonetxt, passwordtxt;

    private String URL = "http://www.mommmmom.esy.es/api/v1/users/signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usertxt = (EditText) findViewById(R.id.usertxt);
        mailtxt = (EditText) findViewById(R.id.mailtxt);
        phonetxt = (EditText) findViewById(R.id.phonetxt);
        passwordtxt = (EditText) findViewById(R.id.passwordtxt);


    }


    public  void cameraClick (View view){


        String user, mail, phone, password;
        Boolean userValid = false, mailValid= false, phoneValid=false, passwordValid=false;

        user= usertxt.getText().toString();
        mail= mailtxt.getText().toString();
        phone= phonetxt.getText().toString();
        password= passwordtxt.getText().toString();


        if(user.equals("")){
            usertxt.setError("name not valid");
        }else{
            userValid=true;
        }

        if(phone.equals("")){
            phonetxt.setError("phone not valid");
        }else{
            phoneValid=true;
        }

        if(!isValidEmailId(mail)){
            mailtxt.setError("mail not valid");
        }else{
            mailValid= true;
        }

        if(!isValidPassword(password)){
            passwordtxt.setError("password must be at least 8 characters with upper and lower case letter, digit and special character");
        }else{
            passwordValid= true;
        }


        if(userValid && mailValid && phoneValid && passwordValid){
            new SignUp().execute(user, mail, password, phone);
        }
    }

    public static boolean isValidEmailId(String email) {
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public  static  boolean isValidPassword(String pass){
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return pass.matches(pattern);
    }

    private class SignUp extends AsyncTask<String, Void, Void> {

        String json="", error="", message="";
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SignUpActivity.this); // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... arg) {
            // TODO Auto-generated method stub
            String name = arg[0];
            String email = arg[1];
            String password = arg[2];
            String phone = arg[3];


            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("phone", phone));

            ServiceHandler serviceClient = new ServiceHandler();

            json = serviceClient.makeServiceCall(URL,
                    ServiceHandler.POST, params);


            Log.d("CreatePredictionRequest", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    error = jsonObj.getString("error");
                    message = jsonObj.getString("msg");

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
                Toast.makeText(SignUpActivity.this,"Signed up successfully ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }else if(error.equals("1")){
                Toast.makeText(SignUpActivity.this,"Server Error: "+message, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SignUpActivity.this,"Unknown Error occur, please try again ", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
