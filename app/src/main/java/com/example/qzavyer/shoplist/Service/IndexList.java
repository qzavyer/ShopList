package com.example.qzavyer.shoplist.Service;

import android.database.Cursor;

import java.util.Hashtable;

/**
 * Класс индексов
 */
class IndexList {
    /**
     * Список индексов
     */
    private Hashtable<String, Integer> indexes = new Hashtable<>();

    IndexList(Cursor cursor, String[] names) {
        for (String name: names) {
            indexes.put(name, cursor.getColumnIndex(name));
        }
    }

    int getIndex(String name){
        if(indexes.isEmpty()) return -1;

        Object index = indexes.get(name);

        if(index == null) return -1;

        return (int)index;
    }
}