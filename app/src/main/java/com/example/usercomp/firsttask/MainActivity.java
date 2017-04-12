package com.example.usercomp.firsttask;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

    RecyclerView recyclerView;
    List<Customer> customers;
    ProgressDialog proDialog;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to the views

        tv = (TextView) findViewById(R.id.exz);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);

//        proDialog = new ProgressDialog(MainActivity.this);
//        proDialog.setMessage("Please wait...");
//        proDialog.setCancelable(false);
//        proDialog.show();

        String url = "https://fsfss.retailcrm.ru/api/v4/customers?apiKey=X8PBNzZE7h7f6BtDuiJn0bLbtYjFpI7r";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                customers = new ArrayList<Customer>();
                                JSONArray customersJsonArr = response.getJSONArray("customers");
                                for (int i=0; i<customersJsonArr.length(); i++)
                                {
                                    JSONObject JSONobj = customersJsonArr.getJSONObject(i);
                                    Customer customer = new Customer();

                                    customer.setSite( JSONobj.has("site") ? JSONobj.getString("site") : "" );;
                                    customer.setEmail( JSONobj.has("email") ? JSONobj.getString("email") : "" );
                                    customer.setPatronymic( JSONobj.has("patronymic") ? JSONobj.getString("patronymic") : "" );
                                    customer.setLastName( JSONobj.has("lastName") ? JSONobj.getString("lastName") : "" );
                                    customer.setFirstName( JSONobj.has("firstName") ? JSONobj.getString("firstName") : "" );
                                    customer.setId(JSONobj.getInt("id"));
                                    if (JSONobj.has("createdAt")) {
                                        String date = JSONobj.getString("createdAt");
                                        java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        try {
                                            customer.setCreatedAt(format.parse(date));
                                        } catch (Exception e) {

                                        }
                                    }

                                    if (JSONobj.has("birthday")) {
                                        String date = JSONobj.getString("birthday");
                                        java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                        try {
                                            customer.setBirthday(format.parse(date));
                                        } catch (Exception e) {

                                        }
                                    }


                                    customers.add(customer);
                                }
                                //if (proDialog.isShowing()) proDialog.dismiss();


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        } else {
                            Log.e("ServiceHandler", "No data received from HTTP Request");
                        }

                        EditCostumer listener = new EditCostumer() {
                            @Override
                            public void onCostumerEdit(Customer costumer) {
                                Log.i("TAG", costumer.toString());
                            }
                        };

                        myAdapter adapter = new myAdapter(MainActivity.this,customers,listener);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);

    }

    public interface EditCostumer {

        public void onCostumerEdit(Customer costumer);
    }

}







