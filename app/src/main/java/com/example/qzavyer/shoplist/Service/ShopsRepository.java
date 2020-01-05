package com.example.qzavyer.shoplist.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qzavyer.shoplist.Models.Shop;

import java.util.ArrayList;

/**
 * Репозиторий магазина
 */
class ShopsRepository {
    private DBHelper dbHelper;

    public ShopsRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * Возвращает магазин по названию
     * @param name Название магазина
     * @return Данные магазина
     */
    public Shop getShopByName(@org.jetbrains.annotations.NotNull String name) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = {name};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query("shop", null, "name = ?", args, null, null, "name");

        Shop item = null;

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");

            item = new Shop();
            item.setId(c.getInt(idColIndex));
            item.setName(c.getString(nameColIndex));
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return item;
    }

    /**
     * Возвращает все магазины
     * @return Список магазинова
     */
    public ArrayList<Shop> allShops(){
        ArrayList<Shop> items = new ArrayList<>();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String table = "shop";

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(table, null, null, null, null, null, "name");

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");

            do {
                Shop item = new Shop();
                item.setId(c.getInt(idColIndex));
                item.setName(c.getString(nameColIndex));

                items.add(item);
            } while (c.moveToNext());
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return items;
    }

    /**
     * Добавляет магазин
     * @param shop Добавляемые данные
     * @return Добавленные данные
     */
    public Shop add(Shop shop) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        cv.put("name", shop.getName());

        // вставляем запись и получаем ее ID
        long rowID = db.insert("shop", null, cv);

        shop.setId((int) rowID);

        return shop;
    }
}