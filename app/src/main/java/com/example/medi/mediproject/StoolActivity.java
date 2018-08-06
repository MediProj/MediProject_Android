package com.example.medi.mediproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StoolActivity extends Activity {

    RequestQueue queue;
    Button bt_prev, bt_next;
    TextView tv_stool_num;
    String stool_num = "1";
    String pid;

    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_stool);

        bt_next = findViewById(R.id.Bnt_next);
        bt_prev = findViewById(R.id.Bnt_prev);
        tv_stool_num = findViewById(R.id.tv_stool_num);

        final Intent intent = getIntent();
        pid=intent.getStringExtra("pid");
        Toast.makeText(getApplicationContext(),pid,Toast.LENGTH_LONG).show();
        String str_user_pk = MediValues.patientData.get(pid).get("user_pk");
        int user_pk = Integer.parseInt(str_user_pk);

        //stool num 얻어오기
        queue = Volley.newRequestQueue(this);
        String url = "http://54.202.222.14/records/api/records-list/";

        JSONArray tmp = new JSONArray();
        JSONObject post = null;

        try {
            post = new JSONObject();
            post.put("patient", user_pk);
            post.put("stool_count",5);
            post.put("liquid_amount", 0.0);
            post.put("consume_amount",0.0);
            post.put("urine_amount", 0.0);
            tmp.put(post);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST,url,post,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                Toast.makeText(getApplicationContext(),"Hello!!!!",Toast.LENGTH_LONG ).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
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

        //next bnt 텍스트 수전
        bt_next.setText("등록");
        tv_stool_num.setText("등록시, 오늘 총 " + stool_num + "회로 기록됩니다");

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StoolActivity.this, TimeDateActivity.class);
                intent2.putExtra("val", 0);
                intent2.putExtra("pid",pid);
                startActivity(intent2);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StoolActivity.this, MenuActivity.class);
                Toast.makeText(getApplicationContext(),"성공적으로 등록되었습니다", Toast.LENGTH_LONG).show();
                intent2.putExtra("pid",pid);
                startActivity(intent2);
            }
        });

    }

}