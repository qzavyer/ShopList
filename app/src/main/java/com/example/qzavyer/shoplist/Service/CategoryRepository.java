package com.example.qzavyer.shoplist.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qzavyer.shoplist.Models.Category;

import java.util.ArrayList;

/**
 * Репозиторий категории
 */
class CategoryRepository {
    private DBHelper dbHelper;

    public CategoryRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * Возвращает категорию по названию
     *
     * @param name Название категории
     * @return Данные категории
     */
    public Category getByName(@org.jetbrains.annotations.NotNull String name) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = {name};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query("categories", null, "name = ?", args, null, null, "name");

        Category item = null;

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");

            item = new Category();
            item.setId(c.getInt(idColIndex));
            item.setName(c.getString(nameColIndex));
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return item;
    }

    /**
     * Возвращает все категории
     *
     * @return Список категорий
     */
    public ArrayList<Category> all() {
        ArrayList<Category> items = new ArrayList<>();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String table = "categories";

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(table, null, null, null, null, null, "name");

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");

            do {
                Category item = new Category();
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
     * Добавляет категорию
     *
     * @param category Добавляемые данные
     * @return Добавленные данные
     */
    public Category add(Category category) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        cv.put("name", category.getName());

        // вставляем запись и получаем ее ID
        long rowID = db.insert("categories", null, cv);

        category.setId((int) rowID);

        return category;
    }
}

