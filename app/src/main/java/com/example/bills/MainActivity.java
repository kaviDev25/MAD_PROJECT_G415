package com.example.bills;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CardView card_1, card_2, card_3, card_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card_1 = findViewById(R.id.card1);//binuri
        card_2 = findViewById(R.id.card2);//anne
        card_3 = findViewById(R.id.card3);//kavinsa
        card_4 = findViewById(R.id.card4);//ayodya

        //card view 3 to direct personal Bill feature
        card_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,BillList.class);
                startActivity(i);
            }
        });
    }
}