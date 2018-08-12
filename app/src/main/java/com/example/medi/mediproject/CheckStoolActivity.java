package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class CheckStoolActivity extends BaseActivity {

    int type=1;
    String pid;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stool_check);

        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");

        String name= MediValues.patientData.get(pid).get("name");
        TextView title_pname = findViewById(R.id.p_name);
        title_pname.setText(name+" 님");

        final RadioButton c1 = findViewById(R.id.c1);
        final RadioButton c2 = findViewById(R.id.c2);
        final RadioButton c3 = findViewById(R.id.c3);
        final RadioButton c4 = findViewById(R.id.c4);
        c1.setChecked(true);

        RadioButton.OnClickListener optionOnClickListener = new RadioButton.OnClickListener() {
            public void onClick(View v) {
                if(c1.isChecked()) {
                    c1.setChecked(false);
                    type = 1;
                }
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
        Intent intent = new Intent(CheckStoolActivity.this, TimeDateActivity.class);
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
