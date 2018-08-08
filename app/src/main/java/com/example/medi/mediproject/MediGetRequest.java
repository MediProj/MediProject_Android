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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MediGetRequest {
    private String url  = "http://54.202.222.14/patients/api/patients-list/";

    MediGetRequest(String pk, final String key, Context context){
        url = url +String.valueOf(pk)+"/";
        RequestQueue queue;
        queue = Volley.newRequestQueue((Context) context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respnse", response.toString());
                try {
                    String tmp= response.getString(key);
                    MediValues.stool_count= Integer.parseInt(tmp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

        queue.add(objectRequest);

    }

}
