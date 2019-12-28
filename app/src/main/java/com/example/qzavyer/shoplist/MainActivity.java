package com.example.qzavyer.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd, btnClear;
    ListView lvSimple;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fillList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(getString(R.string.listTable), null, null);

        // закрываем подключение к БД
        dbHelper.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.btnClear:
                // подключаемся к БД
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                db.delete(getString(R.string.listTable), null, null);
                // закрываем подключение к БД
                dbHelper.close();

                fillList();
                break;
        }
    }

    private void fillList(){
        // упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<>();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // делаем запрос всех данных из таблицы list, получаем Cursor
        Cursor c = db.query(getString(R.string.listTable), null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int nameColIndex = c.getColumnIndex("name");

            Map<String, Object> m;

            do {
                m = new HashMap<>();
                m.put("name", c.getString(nameColIndex));
                data.add(m);
            } while (c.moveToNext());
        }

        c.close();

        // закрываем подключение к БД
        dbHelper.close();

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { "name" };

        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.cbChecked };

        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.list_item, from, to);

        // определяем список и присваиваем ему адаптер
        lvSimple = findViewById(R.id.lvMain);
        lvSimple.setAdapter(sAdapter);
    }
}