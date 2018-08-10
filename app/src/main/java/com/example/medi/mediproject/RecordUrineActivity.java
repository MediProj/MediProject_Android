package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RecordUrineActivity extends BaseActivity {
    String pid;
    private EditText weightUrine;
    private Button sendUrine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_urine);

        Intent intent =getIntent();
        pid=intent.getStringExtra("pid");

        final String str_user_pk = MediValues.patientData.get(pid).get("user_pk");
        int user_pk = Integer.parseInt(str_user_pk);

        weightUrine = (EditText) findViewById(R.id.weightPrint);
        sendUrine = (Button) findViewById(R.id.urineSend);

        buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonNext = (Button) findViewById(R.id.btnNext);

        sendUrine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!weightUrine.getText().toString().equals("")) {
                    MediPostRequest postRequest = new MediPostRequest(
                            Integer.parseInt(str_user_pk),
                            0, 0.0f, 0.0f, Float.parseFloat(weightUrine.getText().toString()), view.getContext());
                }
            }
        });
    }

    public void onPrevClick(View view) {
        Intent intent = new Intent(RecordUrineActivity.this, ContainerSelectActivity.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    public void onNextClick(View view) {
        Intent intent = new Intent(RecordUrineActivity.this, ReportActivity.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }
}

