package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecordConsumeActivity extends BaseActivity {
    String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_consume);

        Intent intent =getIntent();
        pid = intent.getStringExtra("pid");

        buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonNext = (Button) findViewById(R.id.btnNext);

    }

    public void onPrevClick(View view) {
        Intent intent = new Intent(RecordConsumeActivity.this, TimeDateActivity.class);
        intent.putExtra("val",2);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    public void onNextClick(View view) {
        Intent intent = new Intent(RecordConsumeActivity.this, ReportActivity.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }
}

