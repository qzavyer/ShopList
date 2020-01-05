package com.example.qzavyer.shoplist.Service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.qzavyer.shoplist.Models.Bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

class BillRepository{
    private DBHelper dbHelper;

    public BillRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * Добавляет чек
     * @param bills Добавляемые данные
     * @return Добавленные данные
     */
    public ArrayList<Bill> add(ArrayList<Bill> bills) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        db.beginTransaction();

        for (Bill bill : bills) {
            // создаем объект для данных
            ContentValues cv = new ContentValues();

            cv.put("count", bill.getCount()*100);
            cv.put("goodId", bill.getGoodId());
            cv.put("shopId", bill.getShopId());
            cv.put("date", dateFormat.format(bill.getDate()));
            cv.put("price", bill.getPrice()*100);
            cv.put("sum", bill.getSum()*100);

            // вставляем запись и получаем ее ID
            long rowID = db.insert("bill", null, cv);

            bill.setId((int) rowID);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return bills;
    }
}
