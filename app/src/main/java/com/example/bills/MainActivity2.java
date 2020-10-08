package com.example.bills;
//IT19073156
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    DBHandler mydb;
    EditText edit_id,edit_year,edit_month,edit_income_type,edit_income_amount;
    Button btnadd_data,Next;
    Button btnView,btn_update,btndelete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        mydb=new DBHandler(this);
        edit_id=(EditText)findViewById(R.id.et_id);
        edit_year=(EditText)findViewById(R.id.etyear);
        edit_month=(EditText)findViewById(R.id.etmonth);
        edit_income_type=(EditText)findViewById(R.id.et_incometype);
        edit_income_amount=(EditText)findViewById(R.id.et_income_amount);
        btnadd_data=(Button) findViewById(R.id.btnadd);
        btnView=(Button) findViewById(R.id.btn_view);
        btn_update=(Button) findViewById(R.id.btn_update);
        btndelete=(Button) findViewById(R.id.btndelete);
        Next=findViewById(R.id.next);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity2.this, Cal.class);
                startActivity(i);
            }
        });

        addData();
        viewAll();
        UpdateData();
        deleteData();





    }

    public void deleteData(){
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows=mydb.deleteData(edit_id.getText().toString());
                if(deleteRows>0){
                    Toast.makeText(MainActivity2.this, "Data Deleted",Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(MainActivity2.this, "Data not Deleted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void UpdateData(){
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate=mydb.updateData(
                        edit_id.getText().toString(),
                        edit_year.getText().toString(),
                        edit_month.getText().toString(),
                        edit_income_type.getText().toString(),
                        edit_income_amount.getText().toString()
                        );
                if(isUpdate==true){
                    Toast.makeText(MainActivity2.this, "Data Updated",Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(MainActivity2.this, "Data not Updated",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void addData(){
        btnadd_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted=mydb.insertData(edit_year.getText().toString(),
                        edit_month.getText().toString(),
                        edit_income_type.getText().toString(),
                        edit_income_amount.getText().toString());
                if(isInserted=true){
                    Toast.makeText(MainActivity2.this, "data Inserted",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity2.this, "data not Inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void viewAll(){
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res =mydb.getallData();
                if(res.getCount()==0){
                    //show message
                    showMessage("Error","Nothing Found");
                    return ;
                }
                StringBuffer buffer=new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("id :"+res.getString(0)+"\n");
                    buffer.append("year :"+res.getString(1)+"\n");
                    buffer.append("month :"+res.getString(2)+"\n");
                    buffer.append("income_type :"+res.getString(3)+"\n");
                    buffer.append("income_amount :"+res.getString(4)+"\n\n");

                }
                //show all data
                showMessage("Data",buffer.toString());
            }
        });
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}