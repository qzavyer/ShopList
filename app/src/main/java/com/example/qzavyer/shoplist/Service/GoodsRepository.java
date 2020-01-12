package com.example.qzavyer.shoplist.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qzavyer.shoplist.Models.Good;

import java.util.ArrayList;

/**
 * Репозиторий товаров
 */
class GoodsRepository extends CommonRepository {
    GoodsRepository(Context context) {
        super(context);
    }

    /**
     * Возвращает товар по названию
     * @param name название товара
     * @return Данные товара
     */
    Good getGoodByName(@org.jetbrains.annotations.NotNull String name) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = {name};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(dbHelper.GoodsTable, null, "name = ?", args, null, null, "name");

        Good item = null;

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            String[] names = new String[]{"id", "name", "price", "unit", "categoryId"};

            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(c, names);

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
    ArrayList<Good> all() {
        ArrayList<Good> items = new ArrayList<>();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String table = dbHelper.GoodsTable;

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(table, null, null, null, null, null, "name");

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            String[] names = new String[]{"id", "name", "price", "unit", "categoryId"};

            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(c, names);

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
    Good add(Good good) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        cv.put("name", good.getName());
        cv.put("price", good.getPrice() * 100);
        cv.put("unit", good.getUnit());
        cv.put("categoryId", good.getCategoryId());

        // вставляем запись и получаем ее ID
        long rowID = db.insert(dbHelper.GoodsTable, null, cv);

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
        item.setId(cursor.getInt(indexList.getIndex("id")));
        item.setName(cursor.getString(indexList.getIndex("name")));
        item.setPrice(cursor.getInt(indexList.getIndex("price")) / 100d);
        item.setUnit(cursor.getString(indexList.getIndex("unit")));
        item.setCategoryId(cursor.getInt(indexList.getIndex("categoryId")));

        return item;
    }
}