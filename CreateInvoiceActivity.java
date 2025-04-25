package com.example.web;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateInvoiceActivity extends AppCompatActivity {

    EditText customerName, customerMobile, itemName, weight, rate, makingCharges, gstPercent, discount;
    Button addItem, printBill;
    TextView billText;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

        // Link XML views
        customerName = findViewById(R.id.customerName);
        customerMobile = findViewById(R.id.customerMobile);
        itemName = findViewById(R.id.itemName);
        weight = findViewById(R.id.weight);
        rate = findViewById(R.id.rate);
        makingCharges = findViewById(R.id.makingCharges);
        gstPercent = findViewById(R.id.gstPercent);
        discount = findViewById(R.id.discount);
        addItem = findViewById(R.id.addItem);
        printBill = findViewById(R.id.printBill);
        billText = findViewById(R.id.billText);

        dbHelper = new DatabaseHelper(this);

        addItem.setOnClickListener(v -> generateBill());

        printBill.setOnClickListener(v -> {
            Toast.makeText(this, "Bill saved & ready for print.", Toast.LENGTH_SHORT).show();
            clearFields();
        });
    }

    private void generateBill() {
        try {
            String custName = customerName.getText().toString();
            String custMobile = customerMobile.getText().toString();
            String itmName = itemName.getText().toString();
            double wt = Double.parseDouble(weight.getText().toString());
            double rt = Double.parseDouble(rate.getText().toString());
            double making = Double.parseDouble(makingCharges.getText().toString());
            double gst = Double.parseDouble(gstPercent.getText().toString());
            double disc = discount.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(discount.getText().toString());

            double basePrice = wt * rt;
            double totalBeforeTax = basePrice + making - disc;
            double gstAmount = totalBeforeTax * (gst / 100);
            double finalAmount = totalBeforeTax + gstAmount;

            String bill = "📜 INVOICE 📜\n"
                    + "Customer: " + custName + "\n"
                    + "Mobile: " + custMobile + "\n"
                    + "Item: " + itmName + "\n"
                    + "Weight: " + wt + "g\n"
                    + "Rate: ₹" + rt + "\n"
                    + "Making Charges: ₹" + making + "\n"
                    + "Discount: ₹" + disc + "\n"
                    + "GST: " + gst + "% = ₹" + String.format("%.2f", gstAmount) + "\n"
                    + "Total Amount: ₹" + String.format("%.2f", finalAmount);

            billText.setText(bill);

            // Save to database
            dbHelper.insertBill(custName, custMobile, itmName, finalAmount);

            Toast.makeText(this, "Bill Generated & Saved", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Please fill all fields correctly.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        customerName.setText("");
        customerMobile.setText("");
        itemName.setText("");
        weight.setText("");
        rate.setText("");
        makingCharges.setText("");
        gstPercent.setText("");
        discount.setText("");
        billText.setText("Bill Details:");
    }
}
