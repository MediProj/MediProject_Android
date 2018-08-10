package com.example.medi.mediproject.Login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.medi.mediproject.MediValues;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

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