package com.example.medi.mediproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.String.valueOf;

public class ReportActivity extends BaseActivity {
    ListView listView;
    ListViewAdapter listViewAdapter;
    ArrayList <ReportItem> list;
    TextView tv_report_title;
    Button bt_prev,bt_edit;
    String name,pid;
    Date date;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_report);

        //patient info
        final Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        name= MediValues.patientData.get(pid).get("name");
        listView = findViewById(R.id.ReportList);

        TextView title_pname = findViewById(R.id.p_name);
        title_pname.setText(name+" 님");

        //오늘날짜
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DATE);
        String str_date = String.valueOf(month)+"월 "+String.valueOf(day) + "일 ";
        date = new Date(year,month,day);

        bt_prev=findViewById(R.id.Bnt_prev);
        bt_edit = findViewById(R.id.bt_edit);
        tv_report_title = findViewById(R.id.tv_report_date);
        list = new ArrayList<ReportItem>();
        listView = findViewById(R.id.ReportList);

        tv_report_title.setText(name + "님의 "+ str_date + "기록 입니다");


        //임시로
        list.add(new ReportItem(str_date, "대변", "1",""));
        list.add(new ReportItem(str_date, "액체섭취량", "오렌지","120cc"));
        list.add(new ReportItem(str_date, "소변", "거품뇨","100cc"));
        list.add(new ReportItem(str_date, "식사", "점심","밥 1/2\n국 1/4\n반찬1 1"));
        list.add(new ReportItem(str_date, "대변", "1",""));
        list.add(new ReportItem(str_date, "액체섭취량", "오렌지","120cc"));
        list.add(new ReportItem(str_date, "소변", "거품뇨","100cc"));
        list.add(new ReportItem(str_date, "식사", "점심","밥 1/2\n국 1/4\n반찬1 1"));

        listViewAdapter= new ListViewAdapter(getApplicationContext(),list);
        listView.setAdapter(listViewAdapter);

        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        bt_prev.setText("처음으로");
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ReportActivity.this, MenuActivity.class);
                intent2.putExtra("pid", pid);
                startActivity(intent2);
            }
        });
    }

    private class ListViewAdapter extends BaseAdapter{

        private List<ReportItem> list;
        private LayoutInflater inflater;

        private ListViewAdapter(Context context, ArrayList<ReportItem> list){
            this.list = list;
            inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;

            if(view==null){
                view =inflater.inflate(R.layout.report_item,viewGroup,false);
                holder = new ViewHolder();
                holder.tv_time= view.findViewById(R.id.time);
                holder.tv_tag=view.findViewById(R.id.tag);
                holder.tv_val1=view.findViewById(R.id.val1);
                holder.tv_val2=view.findViewById(R.id.val2);
                view.setTag(holder);
            }
            else{
                holder=(ViewHolder)view.getTag();
            }

            holder.tv_time.setText(list.get(i).getDate());
            holder.tv_tag.setText(list.get(i).getTag());
            holder.tv_val1.setText(list.get(i).getVal1());

            if(!(list.get(i).getTag().equals("대변"))){
                holder.tv_val2.setText(list.get(i).getVal2());
            }

            return view;
        }
    }

    public class ViewHolder {
        TextView tv_time,tv_tag, tv_val1,tv_val2;
    }
}
