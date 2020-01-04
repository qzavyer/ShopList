package com.example.qzavyer.shoplist.Service.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table list (" +
                "id integer primary key autoincrement," +
                "goodId integer," +
                "categoryId integer," +
                "count integer," +
                "checked integer);");

        db.execSQL("create table goods (" +
                "id integer primary key autoincrement," +
                "name text," +
                "unit text," +
                "price integer," +
                "categoryId integer);");

        db.execSQL("create table categories (" +
                "id integer primary key autoincrement," +
                "name text);");

        db.execSQL("create table shop (" +
                "id integer primary key autoincrement," +
                "name text);");

        db.execSQL("create table bill (" +
                "id integer primary key autoincrement," +
                "goodId integer," +
                "shopId integer," +
                "date datetime," +
                "count integer," +
                "price integer," +
                "sum integer," +
                "name text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table list;");

        db.execSQL("drop table goods;");

        db.execSQL("drop table categories;");

        db.execSQL("drop table shop;");

        db.execSQL("drop table bill;");

        db.execSQL("create table list (" +
                "id integer primary key autoincrement," +
                "goodId integer," +
                "categoryId integer," +
                "count integer," +
                "checked integer);");

        db.execSQL("create table goods (" +
                "id integer primary key autoincrement," +
                "name text," +
                "unit text," +
                "price integer," +
                "categoryId integer);");

        db.execSQL("create table categories (" +
                "id integer primary key autoincrement," +
                "name text);");

        db.execSQL("create table shop (" +
                "id integer primary key autoincrement," +
                "name text);");

        db.execSQL("create table bill (" +
                "id integer primary key autoincrement," +
                "goodId integer," +
                "shopId integer," +
                "date datetime," +
                "count integer," +
                "price integer," +
                "sum integer," +
                "name text);");
    }
}