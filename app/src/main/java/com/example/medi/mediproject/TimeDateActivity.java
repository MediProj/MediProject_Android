package com.example.medi.mediproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

import static java.util.Calendar.DATE;

public class TimeDateActivity extends Activity {
    Button bt_prev, bt_next, bt_time,bt_date;
    TextView title,tv;
    final int DIALOG_DATE = 1;
    final int DIALOG_TIME = 2;
    String name =null;
    String pid,str_pk;
    int page_id=0;
    int year, month, day, hour, minute,Shour, Sminute;
    boolean date_flag=false;
    boolean time_flag=false;
    boolean today_flag = false;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_tima_date);

        tv=findViewById(R.id.tv);
        bt_next=findViewById(R.id.Bnt_next);
        bt_prev=findViewById(R.id.Bnt_prev);
        bt_date=findViewById(R.id.bt_date);
        bt_time=findViewById(R.id.bt_time);

        //title 표시
        final Intent intent = getIntent();
        page_id= intent.getIntExtra("val",0);
        pid = intent.getStringExtra("pid");
        name = MediValues.patientData.get(pid).get("name");
        str_pk = MediValues.patientData.get(pid).get("pk");

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
                Intent intent2 = new Intent(TimeDateActivity.this,PreActivity.class);
                intent2.putExtra("val",page_id);
                intent2.putExtra("pid",pid);
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
                        MediGetRequest getRequest = new MediGetRequest(str_pk, "stool_count",view.getContext());
                        intent2.putExtra("pid", pid);
                        startActivity(intent2);
                    }
                    else if(page_id==1) {
                        Intent intent2 = new Intent(TimeDateActivity.this, ContainerSelectActivity.class);
                        intent2.putExtra("pid", pid);
                        startActivity(intent2);
                    }

                    else if(page_id==2) {
                        Intent intent2 = new Intent(TimeDateActivity.this,ConsumeMenuActivity.class);
                        intent2.putExtra("pid", pid);
                        startActivity(intent2);
                    }

                }
            }
        });

    }

    public Dialog onCreateDialog(int id){
        today_flag=false;
        Calendar cal = Calendar.getInstance();
        year= cal.get(Calendar.YEAR);
        month= cal.get(Calendar.MONTH);
        day = cal.get(DATE);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute=cal.get(Calendar.MINUTE);

        switch(id){
            case DIALOG_DATE :
                DatePickerDialog datePickerDialog = new DatePickerDialog(TimeDateActivity.this, AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int syear, int smonth, int sday){
                        Toast.makeText(getApplicationContext(),syear+"년 "+(smonth+1)+"월 "+sday +"일을 선택했습니다",Toast.LENGTH_SHORT).show();
                        date_flag=true;

                        if(syear==year && smonth==month && sday==day)
                            today_flag =true;
                    }
                        },year,month,day);

                // DatePicker set up
                datePickerDialog.getDatePicker().updateDate(year, month, day);
                datePickerDialog.getDatePicker().setCalendarViewShown(true);
                datePickerDialog.getDatePicker().setSpinnersShown(false);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setScaleX(2.0f);
                datePickerDialog.getDatePicker().setScaleY(2.0f);
                datePickerDialog.getDatePicker().setPadding(100, 350, 100, 100);
                datePickerDialog.getDatePicker().setForegroundGravity(Gravity.CENTER);

                // CalendarView set up
                datePickerDialog.getDatePicker().getCalendarView().setShowWeekNumber(false);
                datePickerDialog.getDatePicker().getCalendarView().setDate(Calendar.getInstance().getTimeInMillis(), true, true);
                datePickerDialog.getDatePicker().getCalendarView().setUnfocusedMonthDateColor(getResources().getColor(R.color.Gray2));
                datePickerDialog.getDatePicker().setMinimumWidth(1600);
                datePickerDialog.getDatePicker().setMinimumHeight(1300);

                datePickerDialog.show();

                // Dialog Button set up
                datePickerDialog.getButton(Dialog.BUTTON_POSITIVE).setTextSize(40);
                datePickerDialog.getButton(Dialog.BUTTON_POSITIVE).setTypeface(null, Typeface.BOLD);
                datePickerDialog.getButton(Dialog.BUTTON_POSITIVE).setText("확인");
                datePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setTextSize(40);
                datePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setText("취소");

                return datePickerDialog;

            case DIALOG_TIME :
                final TimePickerDialog timePickerDialog = new TimePickerDialog(TimeDateActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT,  new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int shour, int sminute) {
                        Toast.makeText(getApplicationContext(),shour+"시 "+ sminute +"분을 선택했습니다",Toast.LENGTH_SHORT).show();
                        time_flag=true;
                    }
                }, hour,minute,false);

                return timePickerDialog;

        }
        return super.onCreateDialog(id);
    }

}
