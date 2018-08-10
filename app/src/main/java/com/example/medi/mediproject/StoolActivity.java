package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StoolActivity extends BaseActivity {

    Button bt_prev, bt_next;
    TextView tv_stool_num;
    String pid,str_user_pk;
    int user_pk, stool_num=0;

    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_stool);

        bt_next = findViewById(R.id.Bnt_next);
        bt_prev = findViewById(R.id.Bnt_prev);
        tv_stool_num = findViewById(R.id.tv_stool_num);

        final Intent intent = getIntent();
        pid=intent.getStringExtra("pid");
        str_user_pk = MediValues.patientData.get(pid).get("user_pk");
        user_pk = Integer.parseInt(str_user_pk);

        //stool num ++
        stool_num = MediValues.stool_count;

        //next bnt 텍스트 수전
        bt_next.setText("등록");
        tv_stool_num.setText("등록시, 오늘 총 " + String.valueOf(stool_num+1) + "회로 기록됩니다");

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StoolActivity.this, TimeDateActivity.class);
                intent2.putExtra("val", 0);
                intent2.putExtra("pid",pid);
                MediPostRequest postRequest = new MediPostRequest(user_pk,stool_num+1, 0.0f,0.0f,0.0f, view.getContext());
                startActivity(intent2);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StoolActivity.this, ReportActivity.class);
                Toast.makeText(getApplicationContext(),"성공적으로 등록되었습니다", Toast.LENGTH_LONG).show();
                intent2.putExtra("pid",pid);
                startActivity(intent2);
            }
        });

    }

}
