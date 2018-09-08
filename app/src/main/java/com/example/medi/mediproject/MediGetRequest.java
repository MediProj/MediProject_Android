package com.example.medi.mediproject;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MediGetRequest {
    private String url  = "http://54.202.222.14/dashboard/patients/api/patients-dashboard/";

    MediGetRequest(String pk, final String key, Context context){
        url = url + String.valueOf(pk) + "/";
        RequestQueue queue;
        queue = Volley.newRequestQueue((Context) context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("GET_Respnse: ", response.toString());
                try {
                    String tmp = response.getString(key);
                    Log.d("RECORDS_Respnse: ", tmp);
                    parseRecordJSON(response.getJSONArray(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GET_Error: ", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Token " + MediValues.ACCESS_TOKEN;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(objectRequest);
    }

    protected void parseRecordJSON(JSONArray response) {
        MediValues.patientRecord = (Map<String, String>[]) new HashMap<?,?>[response.length()];
        MediValues.pkRecordTag = new String[response.length()];

        for(int i = 0; i < response.length(); i++) {
            try {
                JSONObject entry = response.getJSONObject(i);
                String pk = entry.getString("pk");
                String date = entry.getString("data_date");
                String time = entry.getString("data_time");
                String type = entry.getString("record_type");
                String amount = entry.getString("amount");

                MediValues.patientRecord[i] = new HashMap<>();
                MediValues.pkRecordTag[i] = pk;
                MediValues.patientRecord[i].put("date", date);
                MediValues.patientRecord[i].put("time", time);
                MediValues.patientRecord[i].put("type", type);
                MediValues.patientRecord[i].put("amount", amount);
            } catch (JSONException je){
            }
        }
    }
}
