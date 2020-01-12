package com.example.qzavyer.shoplist.Service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qzavyer.shoplist.Models.Bill;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class BillRepository extends CommonRepository {
    BillRepository(Context context) {
        super(context);
    }

    /**
     * Добавляет чек
     *
     * @param bills Добавляемые данные
     * @return Добавленные данные
     */
    ArrayList<Bill> add(@NotNull ArrayList<Bill> bills) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        db.beginTransaction();

        for (Bill bill : bills) {
            // создаем объект для данных
            ContentValues cv = new ContentValues();

            cv.put("count", bill.getCount() * 100);
            cv.put("goodId", bill.getGoodId());
            cv.put("shopId", bill.getShopId());
            cv.put("date", dateFormat.format(bill.getDate()));
            cv.put("price", bill.getPrice() * 100);
            cv.put("sum", bill.getSum() * 100);
            cv.put("currency", bill.getCurrency());

            // вставляем запись и получаем ее ID
            long rowID = db.insert(dbHelper.BillsTable, null, cv);

            bill.setId((int) rowID);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return bills;
    }

    /**
     * Возвращает последнюю покупку
     *
     * @return Данные о покупке
     */
    Bill getLast() {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(dbHelper.BillsTable, null, null, null, null, null, "date desc");

        Bill item = null;

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            String[] names = new String[]{"id", "count", "goodId", "shopId", "date", "price", "sum", "currency"};

            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(c, names);

            item = getBillFromCursor(c, indexList);
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return item;
    }

    /**
     * Возвращает список покупок товара за последний месяц
     * @param goodId Идентификатор товара
     * @return Список товаров
     */
    ArrayList<Bill> allLastByGood(int goodId) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = {Integer.toString(goodId)};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(dbHelper.BillsTable, null, "goodId = ?", args, null, null, "price desc");

        ArrayList<Bill> items = new ArrayList<>();

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            String[] names = new String[]{"id", "count", "goodId", "shopId", "date", "price", "sum", "currency"};

            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(c, names);

            do{
                Bill item = getBillFromCursor(c, indexList);

                items.add(item);
            }while (c.moveToNext());
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return items;
    }

    /**
     * Возвращает товар из курсора
     *
     * @param cursor    Курсор БД
     * @param indexList Индексы данных
     * @return Данные товара
     */
    private Bill getBillFromCursor(@NotNull Cursor cursor, @NotNull IndexList indexList) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Bill item = new Bill();

        item.setId(cursor.getInt(indexList.getIndex("id")));
        item.setCount(cursor.getInt(indexList.getIndex("count")) / 100d);
        item.setPrice(cursor.getInt(indexList.getIndex("price")) / 100d);
        item.setSum(cursor.getInt(indexList.getIndex("sum")));
        item.setCurrency(cursor.getString(indexList.getIndex("currency")));
        item.setShopId(cursor.getInt(indexList.getIndex("shopId")));
        item.setGoodId(cursor.getInt(indexList.getIndex("goodId")));

        try {
            item.setDate(dateFormat.parse(cursor.getString(indexList.getIndex("date"))));
        } catch (ParseException e) {
            Date current = new Date();
            current.setTime(0);
            item.setDate(current);
        }

        return item;
    }
}