package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecordUrineActivity extends BaseActivity {
    String pid,name, time;
    private TextView weightUrine;
    private Button sendUrine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_urine_ina);

        Intent intent =getIntent();
        pid=intent.getStringExtra("pid");
        name= MediValues.patientData.get(pid).get("name");
        time ="";

        TextView title_pname = findViewById(R.id.p_name);
        title_pname.setText(name+" 님");


        weightUrine = (TextView) findViewById(R.id.urineUnit);

        buttonPrev = (Button) findViewById(R.id.Bnt_prev);
        //buttonNext = (Button) findViewById(R.id.btnNext);
    }

    public void onPrevClick(View view) {
        Intent intent = new Intent(RecordUrineActivity.this, ContainerSelectActivity.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    public void onNextClick(View view) {
        if(!weightUrine.getText().toString().equals("")) {
            MediPostRequest postRequest = new MediPostRequest(view.getContext(), pid, name, MediValues.OUTPUT, MediValues.URINE, -1.0f, time);
            Intent intent = new Intent(RecordUrineActivity.this, ReportActivity.class);
            intent.putExtra("pid", pid);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "무게를 입력해 주세요", Toast.LENGTH_LONG).show();
        }
    }
}

