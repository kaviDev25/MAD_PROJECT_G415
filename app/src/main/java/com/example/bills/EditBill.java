package com.example.bills;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Integer.parseInt;

public class EditBill extends AppCompatActivity {

    private EditText type, amount, paid, dollar;
    private Button add, convert;
    private RadioButton USD, rs;
    private TextView result;
    private DBHandler dbHandler;
    private Context context;
    private Long updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bill);

        context = this;
        dbHandler = new DBHandler(context);

        type = findViewById(R.id.editType);
        amount = findViewById(R.id.editAmount);
        paid = findViewById(R.id.editPaid);
        add = findViewById(R.id.EditAdd);
        dollar = findViewById(R.id.dollar);
        result = findViewById(R.id.rs);
        convert = findViewById(R.id.convert);
        USD = findViewById(R.id.radio1);
        rs = findViewById(R.id.radio2);




        final String id = getIntent().getStringExtra("id");
        ModelClass model = dbHandler.GetSingleBill(parseInt(id));

        type.setText(model.getType());
        amount.setText(model.getAmount());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typeEdit = type.getText().toString();
                String amu = amount.getText().toString();
                String pAid = paid.getText().toString();
                String usd = dollar.getText().toString();
                int amountEdit = parseInt(amu) - parseInt(pAid);

                String amuEdit = String.valueOf(amountEdit);

                updateDate = System.currentTimeMillis();

                ModelClass model = new ModelClass(parseInt(id), typeEdit, amuEdit, updateDate, 0);
                int state = dbHandler.updateBill(model);
                System.out.println(state);
                startActivity(new Intent(context, BusinessBills.class));
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dollar.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Only numbers are allowed", Toast.LENGTH_SHORT).show();
                    return;
                }
                float input = Float.parseFloat(dollar.getText().toString());

                if (USD.isChecked()) {
                    result.setText("Rs " + String.valueOf(convertToRs(input)));
                    USD.setChecked(true);
                    rs.setChecked(false);
                } else {
                    result.setText("USD " + String.valueOf(convertToUSD(input)));
                    USD.setChecked(false);
                    rs.setChecked(true);
                }

            }
        });
    }

    public static float convertToRs(float number) {
        return (float) (number * 184.53);
    }
    public static float convertToUSD(float number) {
        return (float) (number * 0.0054);
    }
}