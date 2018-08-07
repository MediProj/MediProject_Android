package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class SplashActivity extends AppCompatActivity {
    private RequestQueue queue;
    private String authToken;

    public static final String TAG = "MainTAG";

    private String urlToken ="http://54.202.222.14/api-token-auth/";
    private String urlData ="http://54.202.222.14/patients/api/patients-list/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        authToken = "";

        Map<String, String> params = new HashMap<>();
        params.put("username", MediValues.USERNAME);//put your parameters here
        params.put("password", MediValues.PASSWORD);

        MediTokenRequest jsObjRequest = new MediTokenRequest(
                Request.Method.POST, urlToken, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("Response: ", response.toString());
                        StringTokenizer tokens = new StringTokenizer(response.toString(), "\"");
                        tokens.nextToken();
                        tokens.nextToken();
                        tokens.nextToken();
                        MediValues.ACCESS_TOKEN = authToken = tokens.nextToken();

                        getPatientData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response) {
                        Log.d("Response: Error", response.toString());
                    }
                }
        );

        //stringRequest.setTag(TAG);
        jsObjRequest.setTag(TAG);

        queue.add(jsObjRequest);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);


    }


    protected void getPatientData() {
        MediDataRequest jsRequest = new MediDataRequest(urlData,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("Response: ", response.toString());
                        Toast.makeText(getApplicationContext(),"hello", Toast.LENGTH_LONG).show();
                        parsePatientJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response) {
                        Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();

                        Log.d("Response: Error", response.toString());
                    }
                }
        );
        jsRequest.setTag(TAG);

        queue.add(jsRequest);
    }

    protected void parsePatientJSON(JSONArray response) {
        MediValues.patientData = new HashMap<>();

        for(int i = 0; i < response.length(); i++) {
            try {
                JSONObject entry = response.getJSONObject(i);
                String pid = entry.getString("pid");
                String name = entry.getString("name");
                String birth = entry.getString("birth");
                String pk= entry.getString("pk");
                String user_pk= entry.getString("user_pk");

                Map<String, String> temp = new HashMap<>();
                temp.put("name", name);
                temp.put("birth", birth);
                temp.put("pk", pk);
                temp.put("user_pk", user_pk);
                MediValues.patientData.put(pid, temp);
            } catch (JSONException je){
            }
        }
    }

}
