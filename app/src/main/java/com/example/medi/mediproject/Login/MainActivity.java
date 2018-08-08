package com.example.medi.mediproject.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medi.mediproject.MediValues;
import com.example.medi.mediproject.MenuActivity;
import com.example.medi.mediproject.R;

public class MainActivity extends Activity {

    Button bt_login;
    EditText p_name, p_num;
    boolean Login = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_login = findViewById(R.id.bt_login);
        p_name = findViewById(R.id.p_name);
        p_num = findViewById(R.id.p_num);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                CheckRegister();

                if (!Login)
                    Toast.makeText(getApplicationContext(), "잘못된 이름/환자번호 입니다", Toast.LENGTH_LONG).show();

                else {
                    intent.putExtra("pid", p_num.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    public void CheckRegister(){
        String name = p_name.getText().toString();
        String num = p_num.getText().toString();
        Login=false;

        //Wrong patient number
        if(!MediValues.patientData.containsKey(num))
            return;

        if(name.equals(MediValues.patientData.get(num).get("name")))
            Login=true;

    }

}
