package com.example.bills;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
//IT19753836
public class BusinessBills extends AppCompatActivity {


    private Button add;
    private ListView listView;
    private TextView count;
    Context context;
    private DBHandler dbHandler;
    private List<ModelClass> model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_bills);


        dbHandler = new DBHandler(this);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.billList);
        count = findViewById(R.id.count);
        context = this;
        model = new ArrayList<>();
        model = dbHandler.GetAllBills();

        android.widget.Adapter adapter = new com.example.bills.Adapter(context,R.layout.single_bill,model);
        listView.setAdapter((ListAdapter) adapter);

        int countBill = dbHandler.countBill();
        count.setText("You have "+countBill+ " bills to pay");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddBuBill.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ModelClass models = model.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(models.getType());
                builder.setMessage(models.getAmount());

                builder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        models.setFinished(System.currentTimeMillis());
                        dbHandler.updateBill(models);
                        startActivity( new Intent(context,BusinessBills.class));
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dbHandler.DeleteBill(models.getId());
                        startActivity( new Intent(context,BusinessBills.class));
                    }
                });
                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Intent intent = new Intent(context,EditBill.class);
                        intent.putExtra("id",String.valueOf(models.getId()));
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }
}