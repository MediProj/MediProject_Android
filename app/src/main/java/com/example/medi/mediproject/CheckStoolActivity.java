package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class CheckStoolActivity extends BaseActivity {
    RadioButton c1,c2,c3,c4;
    int type=1;
    String pid;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stool_check);

        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");

        c1= findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
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

    }

    protected void onPrevClick(View view) {
        Intent intent = new Intent(CheckStoolActivity.this, PreActivity.class);
        intent.putExtra("pid",pid);
        intent.putExtra("val", 0);
        startActivity(intent);
    }

    protected void onNextClick(View view) {
        Intent intent = new Intent(CheckStoolActivity.this, StoolActivity.class);
        intent.putExtra("pid",pid);
        startActivity(intent);
    }
}
