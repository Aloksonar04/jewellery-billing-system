package com.example.web;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BillHistoryActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper db;
    ArrayList<String> billList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);

        listView = findViewById(R.id.billListView);
        db = new DatabaseHelper(this);
        billList = new ArrayList<>();

        try {
            ArrayList<BillItem> bills = db.getAllItems();

            for (BillItem bill : bills) {
                String entry = "Customer: " + bill.getCustomerName() +
                        "\nItem: " + bill.getItemName() +
                        "\nAmount: ₹" + bill.getAmount();
                billList.add(entry);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, billList);

            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
