package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecordUrineActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_urine);

        buttonPrev.findViewById(R.id.btnPrev);
        buttonNext.findViewById(R.id.btnNext);
    }

    private void onPrevClick(View view) {

    }

    private void onNextClick(View view) {
        Intent intent = new Intent(RecordUrineActivity.this, RecordConsumeActivity.class);
        startActivity(intent);
    }
}

