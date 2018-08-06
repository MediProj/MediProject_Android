package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class VolleyTestActivity extends BaseActivity {

    private Button btn;
    private EditText inp;
    private TextView tv;
    private String inpText;

    private RequestQueue queue;

    private String authToken;

    public static final String TAG = "MainTAG";

    private String urlToken ="http://54.202.222.14/api-token-auth/";
    private String urlData ="http://54.202.222.14/patients/api/patients-list/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);

        btn = findViewById(R.id.btn);
        inp = findViewById(R.id.input);
        tv = findViewById(R.id.textView);

        tv.setMovementMethod(new ScrollingMovementMethod());

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inpText = inp.getText().toString();
                Log.d("MAIN", inpText);
            }
        });

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
                        tv.setText(authToken);

                        getPatientData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response) {
                        Log.d("Response: Error", response.toString());
                        tv.setText("ERROR: " + response.toString());
                    }
                }
        );

        //stringRequest.setTag(TAG);
        jsObjRequest.setTag(TAG);

        queue.add(jsObjRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(queue != null) {
            queue.cancelAll(TAG);
        }
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
                        Log.d("Response: Error", response.toString());
                        tv.setText("ERROR: " + response.toString());
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

                Map<String, String> temp = new HashMap<>();
                temp.put("name", name);
                temp.put("birth", birth);
                temp.put("pk", pk);
                MediValues.patientData.put(pid, temp);
            } catch (JSONException je){
            }
        }

        tv.setText(MediValues.patientData.get("20180105").get("name"));
        Intent intent = new Intent(VolleyTestActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onPrevClick(View view) {

    }

    public void onNextClick(View view) {

    }
}
