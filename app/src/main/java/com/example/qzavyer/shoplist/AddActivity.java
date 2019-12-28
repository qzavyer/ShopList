package com.example.qzavyer.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAdd) {
            EditText editText = findViewById(R.id.editText);

            Editable text = editText.getText();

            dbHelper = new DBHelper(this);

            // подключаемся к БД
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // создаем объект для данных
            ContentValues cv = new ContentValues();

            cv.put("name", text.toString());
            cv.put("checked", 0);

            // вставляем запись и получаем ее ID
            db.insert(getString(R.string.listTable), null, cv);

            // закрываем подключение к БД
            dbHelper.close();

            finish();
        }
    }
}
