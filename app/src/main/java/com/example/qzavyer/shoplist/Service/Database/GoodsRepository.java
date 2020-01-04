package com.example.qzavyer.shoplist.Service.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qzavyer.shoplist.Models.Good;

import java.util.ArrayList;

/**
 * Репозиторий товаров
 */
public class GoodsRepository {
    /**
     * Хэлпер БД
     */
    private DBHelper dbHelper;

    public GoodsRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * Возвращает товар по названию
     * @param name название товара
     * @return Данные товара
     */
    public Good getGoodByName(@org.jetbrains.annotations.NotNull String name) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = {name};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query("goods", null, "name = ?", args, null, null, "name");

        Good item = null;

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(c);

            item = getGoodFromCursor(c, indexList);
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return item;
    }

    /**
     * Возвращает все товары
     * @return Список товаров
     */
    public ArrayList<Good> allGoods() {
        ArrayList<Good> items = new ArrayList<>();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String table = "goods";

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(table, null, null, null, null, null, "name");

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(c);

            do {
                Good item = getGoodFromCursor(c, indexList);

                items.add(item);
            } while (c.moveToNext());
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return items;
    }

    /**
     * Добавляет товар
     * @param good Добавляемые данные
     * @return Добавленные данные
     */
    public Good add(Good good) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        cv.put("name", good.getName());
        cv.put("price", good.getPrice() * 100);
        cv.put("unit", good.getUnit());
        cv.put("categoryId", good.getCategoryId());

        // вставляем запись и получаем ее ID
        long rowID = db.insert("goods", null, cv);

        good.setId((int) rowID);

        return good;
    }

    /**
     * Возвращает товар из курсора
     * @param cursor Курсор БД
     * @param indexList Индексы данных
     * @return Данные товара
     */
    private Good getGoodFromCursor(Cursor cursor, IndexList indexList) {
        Good item = new Good();
        item.setId(cursor.getInt(indexList.getIdIndex()));
        item.setName(cursor.getString(indexList.getNameIndex()));
        item.setPrice(cursor.getInt(indexList.getPriceIndex()) / 100d);
        item.setUnit(cursor.getString(indexList.getUnitIndex()));
        item.setCategoryId(cursor.getInt(indexList.getCatIndex()));

        return item;
    }

    /**
     * Класс индексов
     */
    private class IndexList {
        /**
         * Индекс индетификатора
         */
        private int idIndex;
        /**
         * Индекс названия
         */
        private int nameIndex;
        /**
         * Индекс цены
         */
        private int priceIndex;
        /**
         * Индекс единицы измерения
         */
        private int unitIndex;
        /**
         * Индекс категории
         */
        private int catIndex;

        IndexList(Cursor cursor) {
            idIndex = cursor.getColumnIndex("id");
            nameIndex = cursor.getColumnIndex("name");
            priceIndex = cursor.getColumnIndex("price");
            unitIndex = cursor.getColumnIndex("unit");
            catIndex = cursor.getColumnIndex("categoryId");
        }

        int getIdIndex() {
            return idIndex;
        }

        int getNameIndex() {
            return nameIndex;
        }

        int getPriceIndex() {
            return priceIndex;
        }

        int getUnitIndex() {
            return unitIndex;
        }

        int getCatIndex() {
            return catIndex;
        }
    }
}