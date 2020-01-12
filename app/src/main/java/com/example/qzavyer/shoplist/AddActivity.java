package com.example.qzavyer.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.qzavyer.shoplist.Models.Category;
import com.example.qzavyer.shoplist.Models.ShopItem;
import com.example.qzavyer.shoplist.Service.CategoryService;
import com.example.qzavyer.shoplist.Service.GoodService;
import com.example.qzavyer.shoplist.Service.ShopItemService;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd;
    boolean isBye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();
        isBye = intent.getBooleanExtra("isBye", false);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        GoodService service = new GoodService(this);
        ArrayList<String> goodNames = service.getGoodNames();
        ArrayList<String> goodUnits = service.getUnits();

        CategoryService categoryService = new CategoryService(this);
        ArrayList<String> categoryNames = categoryService.getNames();

        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, goodNames);
        AutoCompleteTextView editText = findViewById(R.id.editText);
        editText.setAdapter(namesAdapter);

        ArrayAdapter<String> unitsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, goodUnits);
        AutoCompleteTextView editUnit = findViewById(R.id.editUnit);
        editUnit.setAdapter(unitsAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categoryNames);
        AutoCompleteTextView editCategory = findViewById(R.id.editCategory);
        editCategory.setAdapter(categoryAdapter);
    }

    @Override
    public void onClick(View v) {
        AutoCompleteTextView editCategory;
        AutoCompleteTextView editUnit;
        switch (v.getId()) {
            case R.id.btnAdd:
                AutoCompleteTextView editText = findViewById(R.id.editText);

                Editable text = editText.getText();

                ShopItemService service = new ShopItemService(this);

                editUnit = findViewById(R.id.editUnit);
                String unit = editUnit.getText().toString();
                if (unit.length() == 0) unit = "шт.";

                editCategory = findViewById(R.id.editCategory);

                EditText editCount = findViewById(R.id.editCount);
                String textCount = editCount.getText().toString();

                double count;

                if (textCount.length() == 0) {
                    count = 1;
                } else {
                    count = Double.parseDouble(textCount);
                }

                ShopItem item = new ShopItem();
                item.setCount(count);
                item.setUnit(unit);
                item.setPrice(0);
                item.setName(text.toString());
                item.setChecked(isBye);

                Category category = new Category();
                category.setName(editCategory.getText().toString());

                item = service.add(item, category);

                Intent intent = new Intent();
                intent.putExtra("id", item.getId());
                setResult(RESULT_OK, intent);

                finish();
                break;
            case R.id.editText:
                AutoCompleteTextView textView = findViewById(R.id.editText);
                textView.showDropDown();
                break;
            case R.id.editCategory:
                editCategory = findViewById(R.id.editCategory);
                editCategory.showDropDown();
                break;
            case R.id.editUnit:
                editUnit = findViewById(R.id.editUnit);
                editUnit.showDropDown();
                break;
        }
    }
}