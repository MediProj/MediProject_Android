package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConsumeMenuActivity extends BaseActivity {
    Button bt_liq, bt_cons;
    Button bt_prev;
    TextView pname;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_menu);

        Intent intent = getIntent();
        final String pid = intent.getStringExtra("pid");
        String name = MediValues.patientData.get(pid).get("name");

        pname=findViewById(R.id.p_name);
        bt_cons=findViewById(R.id.bt_con);
        bt_liq=findViewById(R.id.bt_liq);
        bt_prev=findViewById(R.id.Bnt_prev);

        pname.setText(name+" 님");

        bt_liq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ConsumeMenuActivity.this, RecordLiquidActivity.class);
                intent2.putExtra("pid", pid);
                startActivity(intent2);
            }
        });

        bt_cons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ConsumeMenuActivity.this, RecordConsumeActivity.class);
                intent2.putExtra("pid", pid);
                startActivity(intent2);
            }
        });


        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ConsumeMenuActivity.this, TimeDateActivity.class);
                intent2.putExtra("pid", pid);
                intent2.putExtra("val",2);
                startActivity(intent2);
            }
        });


    }
}
