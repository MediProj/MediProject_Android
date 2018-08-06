package com.example.medi.mediproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

public class MainActivity extends Activity {
    String token = null;
    Button bt_login;
    EditText p_name, p_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_login = findViewById(R.id.bt_login);
        p_name = findViewById(R.id.p_name);
        p_num = findViewById(R.id.p_num);

        final String name = p_name.getText().toString();
        final String number = p_num.getText().toString();

        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://54.202.222.14/api-token-auth/";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "service");
            jsonObject.put("password", "service@service");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //token
        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    token = response.getString("token");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(objectRequest);

        //authorization
        String url_plist = "http://54.202.222.14/patients/api/patients-list/";
        final JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url_plist, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("PostResponse", response.toString());

                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PostError",error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Token " + token);
                return header;
            }
        };


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //로그인 정보 모두 입력한 경우
                if (p_name.getText() != null && p_num.getText().toString() != null) {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("Name", p_name.getText().toString());
                    intent.putExtra("Number", p_num.getText().toString());
                    queue.add(loginRequest);

                    startActivity(intent);
                }
            }
        });
    }

    public boolean Login(){

        return false;
    }
}