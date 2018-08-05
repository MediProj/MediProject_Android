package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecordUrineActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_urine);

        buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonNext = (Button) findViewById(R.id.btnNext);
    }

    public void onPrevClick(View view) {
        Intent intent = new Intent(RecordUrineActivity.this, ContainerSelectActivity.class);
        startActivity(intent);
    }

    public void onNextClick(View view) {
        Intent intent = new Intent(RecordUrineActivity.this, ReportActivity.class);
        startActivity(intent);
    }
}

