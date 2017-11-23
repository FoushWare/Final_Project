package com.example.tom.otgstore;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tom.otgstore.model.UserData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    /***
     *This activity will be updated later too:
     * include the user info and the ability to change userName
     * **/

    /*
    By Mirna @ 17-11-2017
     */
    private TextView userName;
    private TextView mobile;
    private TextView email;
    private TextView credit;
    private String URL = null;
    private UserData userDataModel;
    private TextView update;
    private TextView cancel;
    private EditText userNameText;
    private EditText mobileText;
    private EditText emailText;
    private EditText creditText;
    private TextInputLayout userNameInputLayout;
    private TextInputLayout mobileInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout creditInputLayout;
    private boolean update = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        String token = prefs.getString("token", null);


        URL = "http://www.mommmmom.esy.es/api/v1/users/profile?token=" + token;
        userName = (TextView) findViewById(R.id.user_name_text);
        mobile = (TextView) findViewById(R.id.phone_text);
        email = (TextView) findViewById(R.id.email_text);
        credit = (TextView) findViewById(R.id.credit_text);
        update = (TextView) findViewById(R.id.update);
        cancel = (TextView) findViewById(R.id.cancel);

        userNameText = (EditText) findViewById(R.id.user_name_update);
        mobileText = (EditText) findViewById(R.id.user_phone_update);
        emailText = (EditText) findViewById(R.id.user_email_update);
        creditText = (EditText) findViewById(R.id.user_credit_update);

        userNameInputLayout = (TextInputLayout) findViewById(R.id.user_name_update_layout);
        mobileInputLayout = (TextInputLayout) findViewById(R.id.user_email_update_layout);
        emailInputLayout = (TextInputLayout) findViewById(R.id.user_email_update_layout);
        creditInputLayout = (TextInputLayout) findViewById(R.id.user_credit_update_layout);


        updateUiComponent();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!update) {
                    cancel.setVisibility(View.VISIBLE);
                    userNameInputLayout.setVisibility(View.VISIBLE);
                    mobileInputLayout.setVisibility(View.VISIBLE);
                    emailInputLayout.setVisibility(View.VISIBLE);
                    userName.setVisibility(View.GONE);
                    mobile.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);
                } else {
                    if (userName.getText() == null || userName.getText().equals("")) {
                        userNameInputLayout.setError("Invalid Value");
                    } else if (mobile.getText() == null || mobile.getText().equals("")) {
                        mobileInputLayout.setError("Invalid Value");
                    } else if (email.getText() == null || email.getText().equals("")) {
                        emailInputLayout.setError("Invalid Value");
                    } else if (credit.getText() == null || credit.getText().equals("")) {
                        creditInputLayout.setError("Invalid Value");
                    } else {

                        userNameInputLayout.setVisibility(View.GONE);
                        mobileInputLayout.setVisibility(View.GONE);
                        emailInputLayout.setVisibility(View.GONE);
                        creditInputLayout.setVisibility(View.GONE);
                        userName.setText(userNameText.getText());
                        userName.setVisibility(View.VISIBLE);
                        mobile.setText(mobileText.getText());
                        mobile.setVisibility(View.VISIBLE);
                        email.setText(emailText.getText());
                        email.setVisibility(View.VISIBLE);
                        new  UpdateProfileData().execute(userNameText.getText().toString(),mobileText.getText().toString(),emailText.getText().toString());
                    }
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userNameInputLayout.setVisibility(View.GONE);
                mobileInputLayout.setVisibility(View.GONE);
                emailInputLayout.setVisibility(View.GONE);
                userName.setVisibility(View.VISIBLE);
                mobile.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                credit.setVisibility(View.VISIBLE);
            }

        });


    }

    /*
    By Mirna @ 17-11-2017
     */
    private void updateUiComponent() {

        new getProfileData().execute();
    }

    /*
    By Mirna @ 17-11-2017
     */
    private class getProfileData extends AsyncTask<String, Void, Void> {

        String json = "", error = "", message = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ProfileActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... arg) {

            ServiceHandler serviceClient = new ServiceHandler();

            json = serviceClient.makeServiceCall(URL, ServiceHandler.GET, null);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONObject userData = jsonObj.getJSONObject("data");
                    userDataModel = new UserData();
                    userDataModel.setId(userData.getInt("id"));
                    userDataModel.setName(userData.getString("name"));
                    userDataModel.setEmail(userData.getString("email"));
                    userDataModel.setStatus(userData.getString("status"));
                    userDataModel.setGroupId(userData.getInt("group_id"));
                    userDataModel.setBalance(userData.getString("balance"));
                    userDataModel.setImgUrl(userData.getString("img"));
                    userDataModel.setCreatedAt(userData.getString("created_at"));
                    userDataModel.setUpdatedAt(userData.getString("updated_at"));


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
                userName.setText(userDataModel.getName());
                mobile.setText(userDataModel.getPhone());
                email.setText(userDataModel.getEmail());
                credit.setText(userDataModel.getBalance());

            } else if (error.equals("1")) {
                Toast.makeText(ProfileActivity.this, "Server Error: " + message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Unknown Error occur, please try again ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     By Mirna @ 23-11-2017
      */
    private class UpdateProfileData extends AsyncTask<String, Void, Void> {

        String json = "", error = "", message = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ProfileActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... arg) {

            String name = arg[0];
            String mobile = arg[1];
            String email = arg[2];


            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("phone", mobile));
            params.add(new BasicNameValuePair("email", email));
            ServiceHandler serviceClient = new ServiceHandler();

            json = serviceClient.makeServiceCall(URL, ServiceHandler.GET, params);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONObject userData = jsonObj.getJSONObject("data");
                    userDataModel = new UserData();
                    userDataModel.setId(userData.getInt("id"));
                    userDataModel.setName(userData.getString("name"));
                    userDataModel.setEmail(userData.getString("email"));
                    userDataModel.setStatus(userData.getString("status"));
                    userDataModel.setGroupId(userData.getInt("group_id"));
                    userDataModel.setBalance(userData.getString("balance"));
                    userDataModel.setImgUrl(userData.getString("img"));
                    userDataModel.setCreatedAt(userData.getString("created_at"));
                    userDataModel.setUpdatedAt(userData.getString("updated_at"));


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
                userName.setText(userDataModel.getName());
                mobile.setText(userDataModel.getPhone());
                email.setText(userDataModel.getEmail());
                credit.setText(userDataModel.getBalance());

            } else if (error.equals("1")) {
                Toast.makeText(ProfileActivity.this, "Server Error: " + message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Unknown Error occur, please try again ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
