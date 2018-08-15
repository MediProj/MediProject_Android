package com.example.medi.mediproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class MediPostRequest {

    private  String pid;
    private String name;
    private int io_type;
    private int record_type;
    private String time;
    private float amount;

    private final String url = "http://54.202.222.14/records/api/records-list/";

    MediPostRequest(Context context, String pid, String name, int io_type, int record_type, float amount, String time){

        this.pid = pid;
        this.name = name;
        this.io_type = io_type;
        this.record_type = record_type;
        this.amount=amount;
        this.time = time;

        RequestQueue queue;

        queue = Volley.newRequestQueue(context);
        String url = "http://54.202.222.14/records/api/records-list/";

        JSONArray tmp = new JSONArray();
        JSONObject post = null;

        try {
            post = new JSONObject();
            post.put("patient_pid", pid);
            post.put("patient_name", name);
            post.put("io_type", io_type);
            post.put("record_type", record_type);
            post.put("amount", amount);
            post.put("registered_time",time );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST,url,post,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
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

        queue.add(jsonArrayRequest);
    }
}
