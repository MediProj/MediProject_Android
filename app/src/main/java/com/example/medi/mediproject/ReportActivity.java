package com.example.medi.mediproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.medi.mediproject.Login.MediDataRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static java.lang.String.valueOf;

public class ReportActivity extends BaseActivity {
    ListView listView;
    ListViewAdapter listViewAdapter;
    ArrayList <ReportItem> list;
    TextView tv_report_title;
    Button bt_prev, bt_edit;
    String name,pid, pk;
    Date date;

    private RequestQueue queue;
    public static final String TAG = "ReportTAG";

    private String urlToken ="http://54.202.222.14/api-token-auth/";
    private String urlData ="http://54.202.222.14/dashboard/patients/api/patients-dashboard/";


    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_report);

        //patient info
        final Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        name= MediValues.patientData.get(pid).get("name");
        pk = MediValues.patientData.get(pid).get("pk");

        queue = Volley.newRequestQueue(this);
        urlData = urlData.concat(pk);
        getPatientRecords();

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

        tv_report_title.setText(name + "님의 기록 입니다");


        //임시로
        /*
        list.add(new ReportItem(str_date, "대변", "1",""));
        list.add(new ReportItem(str_date, "액체섭취량", "오렌지","120cc"));
        list.add(new ReportItem(str_date, "소변", "거품뇨","100cc"));
        list.add(new ReportItem(str_date, "식사", "점심","밥 1/2\n국 1/4\n반찬1 1"));
        list.add(new ReportItem(str_date, "대변", "1",""));
        list.add(new ReportItem(str_date, "액체섭취량", "오렌지","120cc"));
        list.add(new ReportItem(str_date, "소변", "거품뇨","100cc"));
        list.add(new ReportItem(str_date, "식사", "점심","밥 1/2\n국 1/4\n반찬1 1"));
        */

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

    public void fillList() {
        for(int i = 0; i < MediValues.patientRecord.length; i++) {
            String date = MediValues.patientRecord[i].get("date");
            String time = MediValues.patientRecord[i].get("time");
            String type = MediValues.patientRecord[i].get("type");
            String amount = MediValues.patientRecord[i].get("amount");

            StringTokenizer tok_date = new StringTokenizer(date, "-");
            date = String.format("%s/%s/%s", tok_date.nextToken(), tok_date.nextToken(), tok_date.nextToken());

            StringTokenizer tok_time = new StringTokenizer(time, ":");
            time = String.format("%s시 %s분", tok_time.nextToken(), tok_time.nextToken());

            list.add(new ReportItem(date, time, type, amount));
        }
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;

            if(view==null){
                view =inflater.inflate(R.layout.report_item,viewGroup,false);
                holder = new ViewHolder();
                holder.tv_time= view.findViewById(R.id.time);
                holder.tv_tag=view.findViewById(R.id.tag);
                holder.tv_val1=view.findViewById(R.id.val1);
                holder.tv_val2=view.findViewById(R.id.val2);
                holder.bt_del = view.findViewById(R.id.btn_delete);
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

            holder.bt_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediDeleteRequest delRecord = new MediDeleteRequest(MediValues.pkRecordTag[i], getApplicationContext());
                    getPatientRecords();
                }
            });

            return view;
        }
    }

    public class ViewHolder {
        TextView tv_time,tv_tag, tv_val1,tv_val2;
        Button bt_del;
    }

    protected void getPatientRecords() {
        MediGetRequest GETrequest = new MediGetRequest(pk, "records", this);
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setTitle("로딩중");
        progress.setMessage("기록 조회 중입니다...");
        progress.setCancelable(false);
        progress.show();

        // 기록 조회를 위해 5초 기다림
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
                fillList();
                listViewAdapter= new ListViewAdapter(getApplicationContext(),list);
                listView.setAdapter(listViewAdapter);
            }
        }, 5000);
    }
}
