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

import java.util.HashMap;
import java.util.Map;

public class MediPostRequest {
    private int user_pk;
    private int stool_count;
    private float liquid_amount;
    private float consume_amount;
    private float urine_amount;

    private final String url = "http://54.202.222.14/records/api/records-list/";

    MediPostRequest(int pk, int stool, float lq, float cs, float ur, Context context) {
        user_pk = pk;
        stool_count = stool;
        liquid_amount = lq;
        consume_amount = cs;
        urine_amount = ur;

        RequestQueue queue;

        queue = Volley.newRequestQueue(context);
        String url = "http://54.202.222.14/records/api/records-list/";

        JSONArray tmp = new JSONArray();
        JSONObject post = null;

        try {
            post = new JSONObject();
            post.put("patient", user_pk);
            post.put("stool_count",stool_count);
            post.put("liquid_amount",liquid_amount);
            post.put("consume_amount",consume_amount);
            post.put("urine_amount",urine_amount);
            tmp.put(post);
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
