package com.example.medi.mediproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends Activity {

    Button bt_login;
    EditText p_name, p_num;
    String urlToken = "http://54.202.222.14/api-token-auth/";
    String urlData = "http://54.202.222.14/patients/api/patients-list/";
    JSONArray arr = new JSONArray();
    boolean volley_fin = false;
    private static String TAG="LoginTAG";
    private RequestQueue queue;
    private String authToken;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = findViewById(R.id.title);
        bt_login = findViewById(R.id.bt_login);
        p_name = findViewById(R.id.p_name);
        p_num = findViewById(R.id.p_num);

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

        //queue.add(stringRequest);
        queue.add(jsObjRequest);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }

        MediDataRequest jsRequest = new MediDataRequest(urlData,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response: ", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response) {
                        Log.d("Response: Error", response.toString());
                    }
                }
        );

        jsRequest.setTag(TAG);
        queue.add(jsRequest);

        //Toast.makeText(getApplicationContext(),String.valueOf(volley_fin),Toast.LENGTH_LONG).show();
        tv.setText(String.valueOf(volley_fin));
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("Name", p_name.getText().toString());
                intent.putExtra("Number", p_num.getText().toString());
                tv.setText(String.valueOf(volley_fin));
                //startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(queue != null) {
            queue.cancelAll(TAG);
        }
    }

}
