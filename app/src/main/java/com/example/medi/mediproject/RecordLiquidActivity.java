package com.example.medi.mediproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecordLiquidActivity extends BaseActivity {
    String pid;
    int user_pk;
    EditText et_liquid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_liquid);

        Intent intent =getIntent();
        pid = intent.getStringExtra("pid");
        user_pk = Integer.parseInt(MediValues.patientData.get(pid).get("user_pk"));

        et_liquid = findViewById(R.id.drinkPrint);
        buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonNext = findViewById(R.id.btnNext);
        buttonNext.setText("등록");
    }

    public void onPrevClick(View view) {
        Intent intent = new Intent(RecordLiquidActivity.this, TimeDateActivity.class);
        intent.putExtra("val",2);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    public void onNextClick(View view) {
        Float liquid=0.0f;

        if(!et_liquid.getText().toString().equals("")) {
            try{
                liquid = Float.parseFloat(et_liquid.getText().toString());

                //POST request
                MediPostRequest postRequest = new MediPostRequest(user_pk, 0,
                        liquid, 0.0f, 0.0f, view.getContext());
                Toast.makeText(getApplicationContext(), "성공적으로 등록되었습니다", Toast.LENGTH_LONG).show();

                //next page
                Intent intent = new Intent(RecordLiquidActivity.this, ReportActivity.class);
                intent.putExtra("pid", pid);
                startActivity(intent);

            }catch (NumberFormatException e){
                Toast.makeText(getApplicationContext(),"올바른 형식으로 입력해 주세요", Toast.LENGTH_LONG).show();
            }
        }

        else
            Toast.makeText(getApplicationContext(),"섭취량을 입력해주세요", Toast.LENGTH_LONG).show();
    }
}

