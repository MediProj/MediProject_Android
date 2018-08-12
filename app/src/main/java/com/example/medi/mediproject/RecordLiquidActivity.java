package com.example.medi.mediproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecordLiquidActivity extends BaseActivity {
    String pid;
    String unit=" 컵";
    int user_pk;
    int picked_item=-1;
    int bt_cnt=0;
    boolean zero_flag=true;
    TextView Menu_name, Menu_amt, plus, minus;
    ListView listView;
    ListViewAdapter listViewAdapter;
    ArrayList<String> MenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_liquid);

        Intent intent =getIntent();
        pid = intent.getStringExtra("pid");
        user_pk = Integer.parseInt(MediValues.patientData.get(pid).get("user_pk"));

        Menu_name=findViewById(R.id.menu_name);
        Menu_amt = findViewById(R.id.drinkPrint);
        plus= findViewById(R.id.plus);
        minus=findViewById(R.id.minus);

        buttonPrev = (Button) findViewById(R.id.Bnt_prev);
        buttonNext = findViewById(R.id.Bnt_next);
        buttonNext.setText("등록");
        listView = findViewById(R.id.menuList);

        MenuList = new ArrayList<>();
        MenuList.add("물");
        MenuList.add("이온음료");
        MenuList.add("사과");
        MenuList.add("오렌지");
        MenuList.add("빵");

        listViewAdapter = new ListViewAdapter(getApplicationContext(),MenuList);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Menu_name.setText(MenuList.get(i));
                picked_item= i;
                bt_cnt=0;
                zero_flag=true;

                if(picked_item==0 || picked_item==1)
                    unit=" 컵";

                else if(picked_item==2 || picked_item==3)
                    unit=" 개";

                else if(picked_item==4)
                    unit=" 쪽";

                Menu_amt.setText("0"+unit);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picked_item != -1) {
                    zero_flag = false;
                    bt_cnt++;
                    Menu_amt.setText(String.valueOf(bt_cnt * 0.25f) + unit);
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(picked_item!=-1) {
                 if (bt_cnt > 0)
                     bt_cnt--;

                 if (bt_cnt == 0)
                     zero_flag = true;

                 Menu_amt.setText(String.valueOf(bt_cnt * 0.25f) + unit);
             }
            }
        });


    }

    public void onPrevClick(View view) {
        Intent intent = new Intent(RecordLiquidActivity.this, TimeDateActivity.class);
        intent.putExtra("val",2);
        intent.putExtra("pid", pid);

        startActivity(intent);
    }

    public void onNextClick(View view) {
        Float liquid=0.0f;

        if(!zero_flag) {
                //cal liq amount
                liquid = cal_liq(picked_item,bt_cnt);

                //POST request
                MediPostRequest postRequest = new MediPostRequest(user_pk, 0,
                        liquid, 0.0f, 0.0f, view.getContext());
                Toast.makeText(getApplicationContext(), "총 "+String.valueOf(liquid)+ "cc가 성공적으로 등록되었습니다", Toast.LENGTH_LONG).show();

                //next page
                Intent intent = new Intent(RecordLiquidActivity.this, ReportActivity.class);
                intent.putExtra("pid", pid);
                startActivity(intent);

        }
        else
            Toast.makeText(getApplicationContext(),"섭취량을 입력해주세요", Toast.LENGTH_LONG).show();
    }

    private class ListViewAdapter extends BaseAdapter{

        private List<String> list;
        private LayoutInflater inflater;

        private ListViewAdapter(Context context, ArrayList<String> list){
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
            RecordLiquidActivity.ViewHolder holder = null;

            if(view==null){
                view =inflater.inflate(R.layout.menu_item,viewGroup,false);
                holder = new ViewHolder();
                holder.tv= view.findViewById(R.id.menu_name);
                view.setTag(holder);
            }
            else{
                holder= (ViewHolder) view.getTag();
            }

            holder.tv.setText(list.get(i));
            return view;
        }
    }

    public class ViewHolder {
        TextView tv;
    }

    float cal_liq(int type, int cnt){
        float res=0.0f, amt = 0.25f*cnt;

        //물이나 이온음료인 경우 200(한컵기준용량) * amt
        if( type==0 || type ==1){
            res= 200.0f*amt;
        }

        //사과 (한개에 261)
        else if(type==2){
            res= 261.0f*amt;
        }

        //오렌지(한개에 174)
        else if(type==3){
            res= 174.0f*amt;
        }

        //빵 (한쪽에 12)
        else if(type==4){
            res = 12.0f*amt;
        }

        return res;

    }
}


