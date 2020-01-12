package com.example.qzavyer.shoplist.Service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.NotNull;

class DBHelper extends SQLiteOpenHelper {
    DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 8);
    }

    final String ShopItemTable = "shopitms";
    final String GoodsTable = "goods";
    final String CategoriesTable = "categories";
    final String ShopsTable = "shops";
    final String BillsTable = "bills";
    final String CurrenciesTable = "currencies";

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        db.execSQL("create table " + ShopItemTable + "(" +
                "id integer primary key autoincrement," +
                "goodId integer," +
                "categoryId integer," +
                "count integer," +
                "checked integer);");

        db.execSQL("create table " + GoodsTable + "(" +
                "id integer primary key autoincrement," +
                "name text," +
                "unit text," +
                "price integer," +
                "categoryId integer);");

        db.execSQL("create table " + CategoriesTable + "(" +
                "id integer primary key autoincrement," +
                "name text);");

        db.execSQL("create table " + ShopsTable + "(" +
                "id integer primary key autoincrement," +
                "name text);");

        db.execSQL("create table " + BillsTable + "(" +
                "id integer primary key autoincrement," +
                "goodId integer," +
                "shopId integer," +
                "date datetime," +
                "count integer," +
                "price integer," +
                "sum integer," +
                "name text," +
                "currency text);");

        db.execSQL("create table " + CurrenciesTable + "(" +
                "code text primary key," +
                "name text," +
                "value integer," +
                "date datetime);");
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table IF EXISTS " + ShopItemTable + ";");

        db.execSQL("drop table IF EXISTS " + GoodsTable + ";");

        db.execSQL("drop table IF EXISTS " + CategoriesTable + ";");

        db.execSQL("drop table IF EXISTS " + ShopsTable + ";");

        db.execSQL("drop table IF EXISTS " + BillsTable + ";");

        db.execSQL("drop table IF EXISTS " + CurrenciesTable + ";");

        db.execSQL("create table " + ShopItemTable + "(" +
                "id integer primary key autoincrement," +
                "goodId integer," +
                "categoryId integer," +
                "count integer," +
                "checked integer);");

        db.execSQL("create table " + GoodsTable + "(" +
                "id integer primary key autoincrement," +
                "name text," +
                "unit text," +
                "price integer," +
                "categoryId integer);");

        db.execSQL("create table " + CategoriesTable + "(" +
                "id integer primary key autoincrement," +
                "name text);");

        db.execSQL("create table " + ShopsTable + "(" +
                "id integer primary key autoincrement," +
                "name text);");

        db.execSQL("create table " + BillsTable + "(" +
                "id integer primary key autoincrement," +
                "goodId integer," +
                "shopId integer," +
                "date datetime," +
                "count integer," +
                "price integer," +
                "sum integer," +
                "name text," +
                "currency text);");

        db.execSQL("create table " + CurrenciesTable + "(" +
                "code text primary key," +
                "name text," +
                "value integer," +
                "date datetime);");
    }
}