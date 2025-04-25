package com.example.web;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnCreateInvoice, btnViewBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateInvoice = findViewById(R.id.btnCreateInvoice);
        btnViewBills = findViewById(R.id.btnViewBills);

        btnCreateInvoice.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreateInvoiceActivity.class));
        });

        btnViewBills.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BillHistoryActivity.class));
        });
    }
}
