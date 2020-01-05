package com.example.qzavyer.shoplist.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qzavyer.shoplist.Models.ShopItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class ShopItemsRepository {
    private DBHelper dbHelper;

    public ShopItemsRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Возвращает все записи текущего списка покупок
    public ArrayList<ShopItem> allItems() {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String table = "list l join goods g on l.goodId=g.id";
        String[] columns = {"l.id, g.name, l.count, g.price, l.checked, g.unit"};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(table, columns, null, null, null, null, "name");

        ArrayList<ShopItem> items = getItemsFromCursor(c);

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return items;
    }

    // Возвращает все записи текущего списка покупок
    public ArrayList<ShopItem> allChecked() {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String table = "list l join goods g on l.goodId=g.id";
        String[] columns = {"l.id, g.name, l.count, g.price, l.checked, g.unit"};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(table, columns, "l.checked=1", null, null, null, "name");

        ArrayList<ShopItem> items = getItemsFromCursor(c);

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return items;
    }

    // Удаляет все записи текущего списка покупок
    public void deleteAll() {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("list", null, null);
        // закрываем подключение к БД
        dbHelper.close();
    }

    // Удаляет все записи текущего списка покупок
    public void deleteAllChecked() {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("list", "checked=1", null);
        // закрываем подключение к БД
        dbHelper.close();
    }

    // Добавляет запись в список покупок
    public ShopItem add(@org.jetbrains.annotations.NotNull ShopItem item) {
        ContentValues listCv = new ContentValues();

        listCv.put("goodId", item.getGoodId());
        listCv.put("count", item.getCount()*100);

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert("list", null, listCv);

        item.setId((int)id);

        return item;
    }

    public void setChecked(int id, boolean checked) {
        ShopItem item = getItemById(id);
        item.setChecked(checked);

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        cv.put("id", item.getId());
        cv.put("goodId", item.getGoodId());
        cv.put("checked", item.isChecked() ? 1 : 0);
        cv.put("count", item.getCount() * 100);

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.update("list", cv, "id = ?", new String[]{Integer.toString(id)});
    }

    public ShopItem getItemById(int id) {
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = {Integer.toString(id)};

        String table = "list l join goods g on l.goodId=g.id";
        String[] columns = {"l.id, g.name, l.count, g.price, l.checked, g.unit, l.goodId"};

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(table, columns, "l.id = ?", args, null, null, "l.id");

        ShopItem item = null;

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int priceColIndex = c.getColumnIndex("price");
            int unitColIndex = c.getColumnIndex("unit");
            int checkedColIndex = c.getColumnIndex("checked");
            int countColIndex = c.getColumnIndex("count");
            int countGoodIndex = c.getColumnIndex("goodId");

            item = new ShopItem();
            item.setId(c.getInt(idColIndex));
            item.setName(c.getString(nameColIndex));
            item.setPrice(c.getInt(priceColIndex) / 100d);
            item.setUnit(c.getString(unitColIndex));
            item.setChecked(c.getInt(checkedColIndex) == 1);
            item.setCount(c.getInt(countColIndex) / 100d);
            item.setGoodId(c.getInt(countGoodIndex));
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        return item;
    }

    private ArrayList<ShopItem> getItemsFromCursor(@NotNull Cursor cursor){
        ArrayList<ShopItem> items = new ArrayList<>();

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (cursor.moveToFirst()) {
            String[] names = new String[]{"id", "name", "price", "count", "unit", "checked"};

            // определяем номера столбцов по имени в выборке
            IndexList indexList = new IndexList(cursor, names);

            do {
                ShopItem item = new ShopItem();
                item.setId(cursor.getInt(indexList.getIndex("id")));
                item.setName(cursor.getString(indexList.getIndex("name")));
                item.setPrice(cursor.getInt(indexList.getIndex("price")) / 100d);
                item.setCount(cursor.getInt(indexList.getIndex("count")) / 100d);
                item.setUnit(cursor.getString(indexList.getIndex("unit")));
                item.setChecked(cursor.getInt(indexList.getIndex("checked")) == 1);

                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }
}