package com.example.medi.mediproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordLiquidActivity extends BaseActivity {
    String pid;
    int user_pk;
    EditText et_liquid;
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

        et_liquid = findViewById(R.id.drinkPrint);
        buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonNext = findViewById(R.id.btnNext);
        buttonNext.setText("등록");

        MenuList.add("물");
        MenuList.add("커피");
        MenuList.add("맥주");
        MenuList.add("과일");
        MenuList.add("음료수");
        MenuList.add("소주");
        MenuList.add("야쿠르트");
        MenuList.add("우유");

        listViewAdapter = new ListViewAdapter(getApplicationContext(),MenuList);
        listView.setAdapter(listViewAdapter);

    }

    public void onPrevClick(View view) {
        Intent intent = new Intent(RecordLiquidActivity.this, TimeDateActivity.class);
        intent.putExtra("val",2);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    public void onNextClick(View view) {
        Float liquid=0.0f;

        if(!et_liquid.getText().toString().equals("")) {
            try{
                liquid = Float.parseFloat(et_liquid.getText().toString());

                //POST request
                MediPostRequest postRequest = new MediPostRequest(user_pk, 0,
                        liquid, 0.0f, 0.0f, view.getContext());
                Toast.makeText(getApplicationContext(), "성공적으로 등록되었습니다", Toast.LENGTH_LONG).show();

                //next page
                Intent intent = new Intent(RecordLiquidActivity.this, ReportActivity.class);
                intent.putExtra("pid", pid);
                startActivity(intent);

            }catch (NumberFormatException e){
                Toast.makeText(getApplicationContext(),"올바른 형식으로 입력해 주세요", Toast.LENGTH_LONG).show();
            }
        }

        else
            Toast.makeText(getApplicationContext(),"섭취량을 입력해주세요", Toast.LENGTH_LONG).show();
    }

    private class ListViewAdapter extends BaseAdapter{
        ArrayList <String> list;
        LayoutInflater layoutInflater;

        ListViewAdapter(Context context, ArrayList<String> list){
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
            MenuViewHolder holder=null;
            if(view==null){
                view =layoutInflater.inflate(R.layout.menu_item,viewGroup,false);
                holder = new MenuViewHolder();
                holder.item_name = findViewById(R.id.menu_name);
                view.setTag(holder);
            }
            else{
                holder= (MenuViewHolder) view.getTag();
            }

            holder.item_name.setText(list.get(i));
            return view;
        }
    }

    public class MenuViewHolder{
        TextView item_name;
    }
}

