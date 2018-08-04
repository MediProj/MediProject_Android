package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PromptBottleActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_bottle);

        buttonPrev.findViewById(R.id.btnPrev);
        buttonNext.findViewById(R.id.btnNext);
    }

    private void onPrevClick(View view) {
        Intent intent = new Intent(PromptBottleActivity.this, ContainerSelectActivity.class);
        startActivity(intent);
    }

    private void onNextClick(View view) {
        Intent intent = new Intent(PromptBottleActivity.this, RecordUrineActivity.class);
        startActivity(intent);
    }
}
