package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PromptGreenActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_green);

        buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonNext = (Button) findViewById(R.id.btnNext);
    }

    public void onPrevClick(View view) {
        Intent intent = new Intent(PromptGreenActivity.this, ContainerSelectActivity.class);
        startActivity(intent);
    }

    public void onNextClick(View view) {
        Intent intent = new Intent(PromptGreenActivity.this, RecordUrineActivity.class);
        startActivity(intent);
    }
}

