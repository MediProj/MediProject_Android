package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ContainerSelectActivity extends BaseActivity {
    private ImageButton imgBtnDiaper;
    private ImageButton imgBtnBottle;
    private ImageButton imgBtnGreen;

    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");

        buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonNext = (Button) findViewById(R.id.btnNext);

        imgBtnDiaper = (ImageButton) findViewById(R.id.imgDiaper);
        imgBtnBottle = (ImageButton) findViewById(R.id.imgBottle);
        imgBtnGreen = (ImageButton) findViewById(R.id.imgGreen);

        imgBtnDiaper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intDiaper = new Intent(ContainerSelectActivity.this, PromptDiaperActivity.class);
                intDiaper.putExtra("pid", pid);
                startActivity(intDiaper);
            }
        });

        imgBtnBottle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intBottle = new Intent(ContainerSelectActivity.this, PromptBottleActivity.class);
                intBottle.putExtra("pid", pid);
                startActivity(intBottle);
            }
        });

        imgBtnGreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intGreen = new Intent(ContainerSelectActivity.this, PromptGreenActivity.class);
                intGreen.putExtra("pid", pid);
                startActivity(intGreen);
            }
        });
    }

    @Override
    public void onPrevClick(View view) {
        Intent intGreen = new Intent(ContainerSelectActivity.this, TimeDateActivity.class);
        intGreen.putExtra("pid", pid);
        intGreen.putExtra("val",1);
        startActivity(intGreen);
    }
}
