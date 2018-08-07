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
        list.add(new ConsumeItem("밥", 0));
        list.add(new ConsumeItem("국", 0));
        list.add(new ConsumeItem("반찬", 0));

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
        public View getView(final int i, View view, ViewGroup viewGroup) {

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

            final ConsumeViewHolder finalHolder = holder;
            final String name= this.list.get(i).name;
            holder.fname.setText(name);

           holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listViewAdapter.list.get(i).amount<=6) {
                        int amount = listViewAdapter.list.get(i).amount;
                        finalHolder.amount.setText(getStringAmount(amount+1));
                        listViewAdapter.updateList(i, name, amount+1);
                    }
                }
            });

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listViewAdapter.list.get(i).amount>0) {
                        int amount=listViewAdapter.list.get(i).amount;
                        finalHolder.amount.setText(getStringAmount(amount-1));
                        listViewAdapter.updateList(i,name, amount-1);
                    }
                }
            });
            return view;
        }

        public String getStringAmount(int amount){
            switch (amount){
                case 0 : return "0";
                case 1 : return "1/4";
                case 2: return "1/2";
                case 3: return "3/4";
                case 4: return "1";
                case 5: return "3/2";
                case 6: return "2";
            }

            return "0";
        }

        public void updateList(int i,String name, int newAmount){

            this.list.remove(i);
            this.list.add(i, new ConsumeItem(name, newAmount));
        }
    }


    class ConsumeViewHolder{
        TextView fname,amount,plus,minus;
    }
}
