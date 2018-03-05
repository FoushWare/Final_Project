package com.example.tom.otgstore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tom.otgstore.models.UserData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mirna on 3/4/2018.
 */

public class ResetPasswordActivity extends Activity {

    private EditText password;
    private EditText confirmPassword;
    private Button submit;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout confirmPasswordInputLayout;
    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);

        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        URL = "http://www.mommmmom.esy.es/api/v1/users/password?token=" + token;

        password = (EditText) findViewById(R.id.password_edit_txt);
        confirmPassword = (EditText) findViewById(R.id.confirm_password_edit_txt);
        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (password.getText().equals(null)) {


                    passwordInputLayout.setError(getString(R.string.password_no_value));
                } else if (confirmPassword.getText().equals(null)) {

                    confirmPasswordInputLayout.setError(getString(R.string.password_no_value));

                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {

                    confirmPasswordInputLayout.setError(getString(R.string.password_mismatch));
                } else {

                    resetPassword();
                }
            }
        });
    }

    private void resetPassword() {
        new ResetPassword().execute();
    }


    private class ResetPassword extends AsyncTask<String, Void, Void> {

        String json = "", error = "", message = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ResetPasswordActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... arg) {

            ServiceHandler serviceClient = new ServiceHandler();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("__VALUE", password.getText().toString()));
            params.add(new BasicNameValuePair("__THE_SAME_PASSWORD__", confirmPassword.getText().toString()));

            json = serviceClient.makeServiceCall(URL, ServiceHandler.POST, params);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONObject userData = jsonObj.getJSONObject("data");

                    error = userData.getString("error");
                    message = jsonObj.getString("msg");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            dialog.dismiss();

            if (error.equals("0")) {
                // update the view
                Toast.makeText(ResetPasswordActivity.this, getString(R.string.password_reseated_successfully), Toast.LENGTH_SHORT).show();
                finish();

            } else if (error.equals("1")) {
                Toast.makeText(ResetPasswordActivity.this, "Server Error: " + message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ResetPasswordActivity.this, "Unknown Error occur, please try again ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
