package com.example.medi.mediproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StoolActivity extends Activity {

    Button bt_prev, bt_next;
    TextView tv_stool_num;
    String stool_num = "1";

    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_stool);

        bt_next = findViewById(R.id.Bnt_next);
        bt_prev = findViewById(R.id.Bnt_prev);
        tv_stool_num = findViewById(R.id.tv_stool_num);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("Name");

        //stool num 얻어오기

        //next bnt 텍스트 수전
        bt_next.setText("등록");
        tv_stool_num.setText("등록시, 오늘 총 " + stool_num + "회로 기록됩니다");

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StoolActivity.this, TimeDateActivity.class);
                startActivity(intent2);

            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StoolActivity.this, MenuActivity.class);
                Toast.makeText(getApplicationContext(), name + "님 성공적으로 등록되었습니다", Toast.LENGTH_LONG).show();
                intent2.putExtra("Name", name);
                startActivity(intent2);

            }
        });

    }

}