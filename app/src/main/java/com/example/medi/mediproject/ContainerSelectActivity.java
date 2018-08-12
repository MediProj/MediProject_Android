package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

        String name= MediValues.patientData.get(pid).get("name");
        TextView title_pname = findViewById(R.id.p_name);
        title_pname.setText(name+" 님");

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
        Intent intGreen = new Intent(ContainerSelectActivity.this, CheckUrineActivity.class);
        intGreen.putExtra("pid", pid);
        startActivity(intGreen);
    }
}
