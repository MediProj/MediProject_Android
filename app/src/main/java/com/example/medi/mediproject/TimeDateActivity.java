package com.example.medi.mediproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimeDateActivity extends Activity {
    Button bt_prev, bt_next, bt_time,bt_date;
    TextView title,tv;
    final int DIALOG_DATE = 1;
    final int DIALOG_TIME = 2;
    String name =null;
    int page_id=0;
    boolean date_flag=false;
    boolean time_flag=false;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_tima_date);

        title=findViewById(R.id.title);
        tv=findViewById(R.id.tv);
        bt_next=findViewById(R.id.Bnt_next);
        bt_prev=findViewById(R.id.Bnt_prev);
        bt_date=findViewById(R.id.bt_date);
        bt_time=findViewById(R.id.bt_time);

        //title 표시
        final Intent intent = getIntent();
        page_id= intent.getIntExtra("val",0);

        switch (page_id){
            //stool
            case 0:
                title.setText("대변 횟수 측정");
                break;
            case 1:
                title.setText("소변량 측정");
                break;
            case 2:
                title.setText("섭취량 기록");
        }

        tv.setText(name +"님 날짜와 시간을 선택하고\n다음 버튼을 눌러주세요");

        //Date
        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_DATE);
            }
        });

        //Time
        bt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_TIME);
            }
        });

        //prev(Menu page)
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(TimeDateActivity.this,MenuActivity.class);
                startActivity(intent2);

            }
        });

        //Next
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!date_flag)
                    Toast.makeText(getApplicationContext(), "날짜를 선택해 주세요", Toast.LENGTH_SHORT).show();

                else if (!time_flag)
                    Toast.makeText(getApplicationContext(), "시간을 선택해 주세요", Toast.LENGTH_SHORT).show();

                //다음 페이지로 이동
                else {
                    if (page_id==0) {
                        Intent intent2 = new Intent(TimeDateActivity.this, StoolActivity.class);
                        startActivity(intent2);
                    }
                    else if(page_id==1) {
                        Intent intent2 = new Intent(TimeDateActivity.this, ContainerSelectActivity.class);
                        startActivity(intent2);
                    }

                    else if(page_id==2) {
                        Intent intent2 = new Intent(TimeDateActivity.this, RecordConsumeActivity.class);
                        startActivity(intent2);
                    }

                }
            }
        });

    }

    public Dialog onCreateDialog(int id){
        switch(id){
            case DIALOG_DATE :
                DatePickerDialog datePickerDialog = new DatePickerDialog(TimeDateActivity.this,
                        new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int month, int day){
                        Toast.makeText(getApplicationContext(),year+"년 "+(month+1)+"월 "+day +"일을 선택했습니다",Toast.LENGTH_SHORT).show();
                        date_flag=true;
                    }
                        },2018,8,5);
                return datePickerDialog;

            case DIALOG_TIME :
                TimePickerDialog timePickerDialog = new TimePickerDialog(TimeDateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                Toast.makeText(getApplicationContext(),hour+"시 "+ minute +"분을 선택했습니다",Toast.LENGTH_SHORT).show();
                                time_flag=true;
                            }
                        }, 12,30,false);
                return timePickerDialog;
        }
        return super.onCreateDialog(id);
    }

}
