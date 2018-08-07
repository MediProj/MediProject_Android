package com.example.medi.mediproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConsumeMenuActivity extends Activity {
    Button bt_liq, bt_cons;
    Button bt_prev;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_menu);

        Intent intent = getIntent();
        final String pid = intent.getStringExtra("pid");

        bt_cons=findViewById(R.id.bt_con);
        bt_liq=findViewById(R.id.bt_liq);
        bt_prev=findViewById(R.id.Bnt_prev);

        bt_liq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ConsumeMenuActivity.this, RecordConsumeActivity.class);
                intent2.putExtra("pid", pid);
                startActivity(intent2);
            }
        });

        bt_cons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ConsumeMenuActivity.this, RecordConsume2Activity.class);
                intent2.putExtra("pid", pid);
                startActivity(intent2);
            }
        });


        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ConsumeMenuActivity.this,MenuActivity.class);
                intent2.putExtra("pid", pid);
                startActivity(intent2);
            }
        });


    }
}