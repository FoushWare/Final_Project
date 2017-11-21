package com.example.tom.otgstore;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tom.otgstore.Adapter.HistoryAdapter;
import com.example.tom.otgstore.model.ItemHistory;
import com.example.tom.otgstore.model.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryAcivity extends AppCompatActivity {

    private RecyclerView historyItemList;
    private HistoryAdapter historyAdapter;
    private String URL = null;
    private ArrayList<ItemHistory> itemHistories;
    private TextView noData;


    /***
     *we will implement this later
     *
     * this will be history of the buying of the user  including what he/she buy
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_acivity);
        URL = "http://www.mommmmom.esy.es/api/v1/users/history?token=";

        historyItemList = (RecyclerView) findViewById(R.id.recyclerView_history);
        noData = (TextView)findViewById(R.id.text_view_history_no_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        historyItemList.setLayoutManager(linearLayoutManager);
        new getHistoryData().execute();




    }

    /*
    By Mirna @ 20-11-2017
     */
    private class getHistoryData extends AsyncTask<String, Void, Void> {

        String json = "", error = "", message = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(HistoryAcivity.this);
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
            json = serviceClient.makeServiceCall(URL + token, ServiceHandler.POST, null);

            itemHistories = new ArrayList<>();
            ItemHistory itemHistory = null;

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray jsonArray = jsonObj.getJSONArray("history");
                    String date =  null;
                    for (int i = 0 ; i< jsonArray.length() ; i++){
                         date  = jsonArray.getJSONObject(i).getString("date");
                        JSONArray items = jsonArray.getJSONObject(i).getJSONArray("items");

                        for(int k = 0 ; k< items.length() ; k++){
                            itemHistory= new ItemHistory();
                            itemHistory.setDate(date);
                            itemHistory.setName(items.getJSONObject(k).getString("name"));
                            itemHistory.setPrice(Double.parseDouble(items.getJSONObject(k).getString("price")));
                            itemHistory.setQuantity(Integer.parseInt(items.getJSONObject(k).getString("quantity")));
                            itemHistories.add(itemHistory);

                        }
                    }

                    error = jsonObj.getString("error");
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

            if(itemHistories.size()>0){
                historyItemList.setAdapter(new HistoryAdapter(itemHistories, HistoryAcivity.this));
            }else{
                historyItemList.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);

            }



            dialog.dismiss();

            if (error.equals("0")) {


            } else if (error.equals("1")) {
                Toast.makeText(HistoryAcivity.this, "Server Error: " + message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HistoryAcivity.this, "Unknown Error occur, please try again ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
