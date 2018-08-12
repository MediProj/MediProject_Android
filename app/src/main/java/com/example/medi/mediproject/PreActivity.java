package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class PreActivity extends BaseActivity {
    TextView tv;
    Button bt_next, bt_prev, bt_edit;
    int page_id;
    int year, month, day,hour, minute;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_page);

        tv = findViewById(R.id.tv);
        bt_edit=findViewById(R.id.bt_edit);
        bt_next=findViewById(R.id.Bnt_next);
        bt_prev=findViewById(R.id.Bnt_prev);

        final Intent intent = getIntent();
        final String pid = intent.getStringExtra("pid");
        page_id = intent.getIntExtra("val", 0);
        String name = MediValues.patientData.get(pid).get("name");

        Calendar cal = Calendar.getInstance();
        year= cal.get(Calendar.YEAR);
        month= cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DATE);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute=cal.get(Calendar.MINUTE);
        String time = year+"년 "+month +"월 " + day +"일 "+ hour +"시 " + minute+"분";
        String title="";

        if(page_id==0)
            title="대변 횟수 측정";


        else if(page_id==1)
            title="소변량 측정";

        else  if(page_id==2)
            title="섭취량 측정";

        tv.setText(name+"님 \n" + time+"으로\n 기록을 원하시면 다음을 눌러주세요");

        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PreActivity.this,TimeDateActivity.class);
                intent2.putExtra("pid", pid);
                intent2.putExtra("val", page_id);
                startActivity(intent2);
            }
        });
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PreActivity.this, MenuActivity.class);
                intent2.putExtra("pid", pid);
                startActivity(intent2);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //다음 페이지로 이동
                if (page_id == 0) {
                    Intent intent2 = new Intent(PreActivity.this, CheckStoolActivity.class);
                    intent2.putExtra("pid", pid);
                    intent2.putExtra("val", page_id);
                    startActivity(intent2);
                    }
                else if (page_id == 1) {
                    Intent intent2 = new Intent(PreActivity.this, CheckUrineActivity.class);
                    intent2.putExtra("pid", pid);
                    intent2.putExtra("val", page_id);
                    startActivity(intent2);

                }
                else if (page_id == 2) {
                    Intent intent2 = new Intent(PreActivity.this, ConsumeMenuActivity.class);
                    intent2.putExtra("pid", pid);
                    intent2.putExtra("val", page_id);
                    startActivity(intent2);
                }
            }
        });


    }
}
