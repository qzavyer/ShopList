package com.example.qzavyer.shoplist.Service;

import android.content.Context;

class CommonRepository{
    /**
     * Хэлпер доступа к БД
     */
    DBHelper dbHelper;

    CommonRepository(Context context){
        dbHelper = new DBHelper(context);
    }
}
