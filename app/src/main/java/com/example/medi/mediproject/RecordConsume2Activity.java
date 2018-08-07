package com.example.medi.mediproject;

import android.app.Activity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class RecordConsume2Activity extends Activity {
    Button bt_next,bt_prev;
    ArrayList <ConsumeItem> list;
    ListViewAdapter listViewAdapter;
    ListView listView;
    String pid;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_consume);

        Intent intent = getIntent();
        pid= intent.getStringExtra("pid");

        bt_next=findViewById(R.id.Bnt_next);
        bt_prev=findViewById(R.id.Bnt_prev);

        list = new ArrayList<>();
        list.add(new ConsumeItem("밥", "0"));
        list.add(new ConsumeItem("국", "0"));
        list.add(new ConsumeItem("반착", "0"));

        listView = findViewById(R.id.ConsumeList);

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RecordConsume2Activity.this, ConsumeMenuActivity.class);
                intent2.putExtra("pid",pid);
                startActivity(intent2);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RecordConsume2Activity.this, ReportActivity.class);
                Toast.makeText(getApplicationContext(),"성공적으로 등록되었습니다", Toast.LENGTH_LONG).show();
                intent2.putExtra("pid",pid);
                startActivity(intent2);
            }
        });

        listViewAdapter = new ListViewAdapter(list,getApplicationContext());
        listView.setAdapter(listViewAdapter);

    }


    private class ListViewAdapter extends BaseAdapter{
        private ArrayList<ConsumeItem> list;
        private LayoutInflater layoutInflater;

        ListViewAdapter(ArrayList<ConsumeItem> list, Context context){
            this.list=list;
            layoutInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
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

            ConsumeViewHolder holder=null;

            if(view==null){
                view =layoutInflater.inflate(R.layout.consume_item,viewGroup,false);
                holder = new ConsumeViewHolder();
                holder.amount= view.findViewById(R.id.amount);
                holder.plus=view.findViewById(R.id.plus);
                holder.minus=view.findViewById(R.id.minus);
                holder.fname=view.findViewById(R.id.name);
                view.setTag(holder);
            }
            else{
                holder=(ConsumeViewHolder)view.getTag();
            }

            String str_amount=list.get(i).amount;
            final int amount = Integer.parseInt(str_amount);
            final ConsumeViewHolder finalHolder = holder;

            holder.fname.setText(list.get(i).name);

           holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(amount<5) {
                        finalHolder.amount.setText(String.valueOf(amount + 1));
                    }
                }
            });

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(amount>0) {
                        finalHolder.amount.setText(String.valueOf(amount - 1));
                    }
                }
            });

            return view;
        }
    }


    class ConsumeViewHolder{
        TextView fname,amount,plus,minus;
    }
}
