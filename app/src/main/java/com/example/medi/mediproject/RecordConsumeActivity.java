package com.example.medi.mediproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordConsumeActivity extends BaseActivity {
    Button bt_next,bt_prev;
    int user_pk,type;
    ArrayList <ConsumeItem> list;
    ListViewAdapter listViewAdapter;
    ListView listView;
    String pid;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_consume);

        Intent intent = getIntent();
        pid= intent.getStringExtra("pid");
        user_pk = Integer.parseInt(MediValues.patientData.get(pid).get("user_pk"));

        String name= MediValues.patientData.get(pid).get("name");
        TextView title_pname = findViewById(R.id.p_name);
        title_pname.setText(name+" 님");

        bt_next=findViewById(R.id.Bnt_next);
        bt_prev=findViewById(R.id.Bnt_prev);
        bt_next.setText("등록");

        //아,점,저,기타
        final RadioButton c1 = findViewById(R.id.c1);
        final RadioButton c2 = findViewById(R.id.c2);
        final RadioButton c3 = findViewById(R.id.c3);
        final RadioButton c4 = findViewById(R.id.c4);
        c1.setChecked(true);

        RadioButton.OnClickListener optionOnClickListener = new RadioButton.OnClickListener() {
            public void onClick(View v) {
                if(c1.isChecked())
                    type =1;
                else if(c2.isChecked())
                    type =2;
                else if(c3.isChecked())
                    type=3;
                else
                    type=4;
            }
        };

        c1.setOnClickListener(optionOnClickListener);
        list = new ArrayList<>();
        list.add(new ConsumeItem("밥", 0));
        list.add(new ConsumeItem("국", 0));
        list.add(new ConsumeItem("반찬1", 0));
        list.add(new ConsumeItem("반찬2", 0));
        list.add(new ConsumeItem("후식", 0));

        listView = findViewById(R.id.ConsumeList);

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RecordConsumeActivity.this, ConsumeMenuActivity.class);
                intent2.putExtra("pid",pid);
                startActivity(intent2);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RecordConsumeActivity.this, ReportActivity.class);

                //식사량 정보 받아서 POST 요청 (type/밥/국/반찬)
                float cs = type*100000+ list.get(0).amount*10000 + list.get(1).amount*1000 + list.get(2).amount*100+ list.get(3).amount*10 + list.get(4).amount ;
                MediPostRequest postRequest = new MediPostRequest(user_pk,0,0.0f,cs,0.0f,view.getContext());

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
                holder.img=view.findViewById(R.id.img);
                view.setTag(holder);
            }
            else{
                holder=(ConsumeViewHolder)view.getTag();
            }

            final ConsumeViewHolder finalHolder = holder;
            final String name= this.list.get(i).name;
            holder.fname.setText(name);

            switch(i) {
                case 0:
                    holder.img.setImageResource(R.drawable.c1);
                    break;
                case 1:
                    holder.img.setImageResource(R.drawable.c2);
                    break;
                case 2:
                    holder.img.setImageResource(R.drawable.c3_2);
                    break;
                case 3:
                    holder.img.setImageResource(R.drawable.c4_2);
                    break;
                case 4:
                    holder.img.setImageResource(R.drawable.c5);
                    break;
            }


            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listViewAdapter.list.get(i).amount<6) {
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
        ImageView img;
        TextView fname,amount,plus,minus;

    }
}
