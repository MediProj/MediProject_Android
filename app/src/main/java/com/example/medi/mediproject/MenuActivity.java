package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medi.mediproject.Login.MainActivity;

public class MenuActivity extends BaseActivity {
    TextView tv,tv_stool, tv_urine, tv_consume,tv_report;
    TextView bt_prev;
    String name,pid,pk;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Intent intent=getIntent();

        //Patient Info (name/number)
        pid= intent.getStringExtra("pid");
        name= MediValues.patientData.get(pid).get("name");

        tv =findViewById(R.id.tv);
        tv_stool=findViewById(R.id.stool);
        tv_urine=findViewById(R.id.urine);
        tv_consume=findViewById(R.id.consume);
        tv_report =findViewById(R.id.report);
        bt_prev=findViewById(R.id.Bnt_logout);

        //인사
        tv.setText(name + "님 안녕하세요! 메뉴를 선택해 주세요");

        //Stool page
        tv_stool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MenuActivity.this,PreActivity.class);
                intent2.putExtra("val",0);
                intent2.putExtra("pid", pid);
                startActivity(intent2);
            }
        });

        //Urine
        tv_urine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MenuActivity.this,PreActivity.class);
                intent2.putExtra("val",1);
                intent2.putExtra("pid", pid);
                startActivity(intent2);

            }
        });

        //Consume
        tv_consume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MenuActivity.this,PreActivity.class);
                intent2.putExtra("val",2);
                intent2.putExtra("pid", pid);
                startActivity(intent2);
            }
        });

        //Report
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MenuActivity.this,ReportActivity.class);
                intent2.putExtra("pid", pid);
                startActivity(intent2);

            }
        });

        //Previous => logout
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MenuActivity.this,MainActivity.class);
                Toast.makeText(getApplicationContext(),"로그아웃", Toast.LENGTH_SHORT).show();
                startActivity(intent2);
            }
        });

    }

}
