package com.example.medi.mediproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class MainActivity extends Activity {

    Button bt_login;
    EditText p_name, p_num;
    String urlToken = "http://54.202.222.14/api-token-auth/";
    String urlData = "http://54.202.222.14/patients/api/patients-list/";
    boolean Login = false;
    private static String TAG="LoginTAG";
    private RequestQueue queue;
    String authToken;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = findViewById(R.id.title);
        bt_login = findViewById(R.id.bt_login);
        p_name = findViewById(R.id.p_name);
        p_num = findViewById(R.id.p_num);
/*
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

        //queue.add(stringRequest);
        queue.add(jsObjRequest);

*/
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("Name", p_name.getText().toString());
                intent.putExtra("Number", p_num.getText().toString());
                CheckRegister();
                tv.setText(String.valueOf(Login));
                //                 startActivity(intent);
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
                String pk = entry.getString("pk");

                Map<String, String> temp = new HashMap<>();
                temp.put("name", name);
                temp.put("birth", birth);
                temp.put("pk",pk);
                MediValues.patientData.put(pid, temp);
            } catch (JSONException je){
            }
        }
        Toast.makeText(getApplicationContext(),"Fin", Toast.LENGTH_LONG).show();
    }

    public void CheckRegister(){
        String name = p_name.getText().toString();
        String num = p_num.getText().toString();
        Login=false;

        //Wrong patient number
        if(!MediValues.patientData.containsKey(num))
            return;

        if(name.equals(MediValues.patientData.get(num).get("name")))
            Login=true;

    }

}
