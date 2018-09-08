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

public class MediDeleteRequest {
    private String url  = "http://54.202.222.14/dashboard/patients/api/records-dashboard/";

    MediDeleteRequest(String pk, Context context){
        url = url + String.valueOf(pk) + "/";
        RequestQueue queue;
        queue = Volley.newRequestQueue((Context) context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.DELETE, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DELETE_Response: ", response.toString());
                /*
                try {
                    Log.d("RECORDS_Response: ", tmp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DELETE_Error: ", error.toString());
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
}
