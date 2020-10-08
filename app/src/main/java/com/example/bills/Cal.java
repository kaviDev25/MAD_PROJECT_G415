package com.example.bills;
//IT19073156
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Cal extends AppCompatActivity {

    EditText a,b;
    Button addition;
    TextView tv_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);
        a=findViewById(R.id.et1);
        b=findViewById(R.id.et2);
        addition=findViewById(R.id.add);
        tv_res=findViewById(R.id.tv_res);
    }
    public void setAddition(View view){
        int x,y,result;
        x=Integer.parseInt(a.getText().toString());
        y=Integer.parseInt(b.getText().toString());
        result=x+y;
        tv_res.setText(String.valueOf(result));
    }
}