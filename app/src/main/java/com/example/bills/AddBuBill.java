package com.example.bills;
//IT19753836
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddBuBill extends AppCompatActivity {

    private EditText type, amount, paid;
    private Button add;

    private DBHandler dbHandler;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bu_bill);

        type = findViewById(R.id.addType);
        amount = findViewById(R.id.addAmount);
        add = findViewById(R.id.addAdd);
        context= this;
        dbHandler = new DBHandler(context);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = type.getText().toString();
                String userAmount = amount.getText().toString();
                long started = System.currentTimeMillis();

                ModelClass model = new ModelClass(userType,userAmount,started,0);
                dbHandler.addBill(model);

                startActivity(new Intent(context,BusinessBills.class));
            }
        });


    }



}

