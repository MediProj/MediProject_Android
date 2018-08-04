package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecordConsumeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_consume);

        buttonPrev.findViewById(R.id.btnPrev);
        buttonNext.findViewById(R.id.btnNext);
    }

    private void onPrevClick(View view) {
        Intent intent = new Intent(RecordConsumeActivity.this, RecordUrineActivity.class);
        startActivity(intent);
    }

    private void onNextClick(View view) {

    }
}

