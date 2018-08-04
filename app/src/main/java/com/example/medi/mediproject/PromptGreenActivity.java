package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PromptGreenActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_green);

        buttonPrev.findViewById(R.id.btnPrev);
        buttonNext.findViewById(R.id.btnNext);
    }

    private void onPrevClick(View view) {
        Intent intent = new Intent(PromptGreenActivity.this, ContainerSelectActivity.class);
        startActivity(intent);
    }

    private void onNextClick(View view) {
        Intent intent = new Intent(PromptGreenActivity.this, RecordUrineActivity.class);
        startActivity(intent);
    }
}

