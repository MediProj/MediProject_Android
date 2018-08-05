package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecordConsumeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_consume);

        buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonNext = (Button) findViewById(R.id.btnNext);
    }

    public void onPrevClick(View view) {
        Intent intent = new Intent(RecordConsumeActivity.this, TimeDateActivity.class);
        intent.putExtra("val",2);
        startActivity(intent);
    }

    public void onNextClick(View view) {
        Intent intent = new Intent(RecordConsumeActivity.this, ReportActivity.class);
        startActivity(intent);
    }
}

