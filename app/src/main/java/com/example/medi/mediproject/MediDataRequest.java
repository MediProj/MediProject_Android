package com.example.medi.mediproject;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;

public class MediDataRequest extends JsonArrayRequest {


    public MediDataRequest(String url, Listener<JSONArray> listener,
                         ErrorListener errorListener) {
        super(url, listener, errorListener);

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        String auth = "Token " + MediValues.ACCESS_TOKEN;
        headers.put("Authorization", auth);
        return headers;
    }


}