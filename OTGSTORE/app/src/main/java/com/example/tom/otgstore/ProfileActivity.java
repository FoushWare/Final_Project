package com.example.tom.otgstore;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
     *This activity will be later too:
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        URL = "http://www.mommmmom.esy.es/api/v1/users/profile?token=TOKEN";
        userName = (TextView) findViewById(R.id.user_name_text);
        mobile = (TextView) findViewById(R.id.phone_text);
        email = (TextView) findViewById(R.id.email_text);
        credit = (TextView) findViewById(R.id.credit_text);

        updateUiComponent();
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
            SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
            String token = prefs.getString("token", null);
            json = serviceClient.makeServiceCall(URL+token, ServiceHandler.GET, null);


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
