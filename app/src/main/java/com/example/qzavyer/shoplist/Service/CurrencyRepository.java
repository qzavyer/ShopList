package com.example.qzavyer.shoplist.Service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qzavyer.shoplist.Models.Currency;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Репозитори валют
 */
class CurrencyRepository extends CommonRepository{
    CurrencyRepository(Context context) {
        super(context);
    }

    /**
     * Возвращает названия валют
     * @return Список названий валют
     */
    ArrayList<Currency> all(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(dbHelper.CurrenciesTable, null, null, null, null, null, "name");

        ArrayList<Currency> list = new ArrayList<>();

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            String[] names = new String[]{"code", "name", "value", "date"};

            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(c, names);

            do {
                Currency item = getCurrencyFromCursor(c, indexList);

                list.add(item);
            } while (c.moveToNext());
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return list;
    }

    /**
     * Сохраняет валюту
     * @param currency Сохраняемые данные
     * @return Сохранённые данные
     */
    Currency update(@NotNull Currency currency) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cv.put("code", currency.getCode());
        cv.put("name", currency.getName());
        cv.put("value", (int)(currency.getValue()*10000));
        cv.put("date", dateFormat.format(currency.getDate()));

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.update(dbHelper.CurrenciesTable, cv, "code = ?", new String[]{currency.getCode()});

        return currency;
    }

    /**
     * Добавляет валюту
     * @param currency Добавляемые данные
     * @return Добавленные данные
     */
    Currency add(@NotNull Currency currency){
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        db.beginTransaction();

        ContentValues cv = new ContentValues();

        cv.put("code", currency.getCode());
        cv.put("name", currency.getName());
        cv.put("value", (int)(currency.getValue()*100));
        cv.put("date", dateFormat.format(currency.getDate()));

        db.insert(dbHelper.CurrenciesTable, null, cv);

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return currency;
    }

    /**
     * Возвращает валюту по коду
     * @param code Код валюты
     * @return Данные валюты
     */
    Currency getByCode(@NotNull String code){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = {code};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(dbHelper.CurrenciesTable, null, "code = ?", args, null, null, "name");

        Currency item = null;

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            String[] names = new String[]{"code", "name", "value", "date"};

            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(c, names);

            item = getCurrencyFromCursor(c, indexList);
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return item;
    }

    /**
     * Возвращает валюту по имени
     * @param name Имя валюты
     * @return Данные валюты
     */
    Currency getByName(@NotNull String name){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = {name};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(dbHelper.CurrenciesTable, null, "name = ?", args, null, null, "name");

        Currency item = null;

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            String[] names = new String[]{"code", "name", "value", "date"};

            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(c, names);

            item = getCurrencyFromCursor(c, indexList);
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return item;
    }

    /**
     * Возвращает валюту из курсора
     * @param cursor Курсор БД
     * @param indexList Индексы данных
     * @return Данные валюты
     */
    private Currency getCurrencyFromCursor(@NotNull Cursor cursor, @NotNull IndexList indexList) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Currency item = new Currency();
        item.setCode(cursor.getString(indexList.getIndex("code")));
        item.setName(cursor.getString(indexList.getIndex("name")));
        item.setValue(cursor.getInt(indexList.getIndex("value")) / 10000d);
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
