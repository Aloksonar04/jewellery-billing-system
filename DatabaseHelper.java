package com.example.web;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "jewelry_billing.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_BILLS = "bills";

    private static final String COL_ID = "id";
    private static final String COL_CUSTOMER_NAME = "customer_name";
    private static final String COL_MOBILE = "mobile";
    private static final String COL_ITEM = "item_name";
    private static final String COL_AMOUNT = "amount";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_BILLS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CUSTOMER_NAME + " TEXT, "
                + COL_MOBILE + " TEXT, "
                + COL_ITEM + " TEXT, "
                + COL_AMOUNT + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        onCreate(db);
    }

    // Insert a new bill
    public boolean insertBill(String name, String mobile, String item, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CUSTOMER_NAME, name);
        values.put(COL_MOBILE, mobile);
        values.put(COL_ITEM, item);
        values.put(COL_AMOUNT, amount);

        long result = db.insert(TABLE_BILLS, null, values);
        return result != -1;
    }

    // Fetch all bills
    public ArrayList<BillItem> getAllItems() {
        ArrayList<BillItem> billList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM bills", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                BillItem bill = new BillItem();
                bill.setCustomerName(cursor.getString(cursor.getColumnIndexOrThrow("customer_name")));
                bill.setMobile(cursor.getString(cursor.getColumnIndexOrThrow("mobile")));
                bill.setItemName(cursor.getString(cursor.getColumnIndexOrThrow("item_name")));
                bill.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("amount")));

                billList.add(bill);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return billList;
    }

}
