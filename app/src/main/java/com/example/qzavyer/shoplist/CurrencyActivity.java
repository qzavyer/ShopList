package com.example.qzavyer.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qzavyer.shoplist.Models.Currency;
import com.example.qzavyer.shoplist.Service.CurrencyService;

import java.util.Date;

public class CurrencyActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSave;
    EditText editCode;
    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        editCode = findViewById(R.id.editCode);
        editName = findViewById(R.id.editName);
    }

    @Override
    public void onClick(View v) {
        if (editCode.getText().toString().length() == 0) return;
        if (editName.getText().toString().length() == 0) return;

        CurrencyService currencyService = new CurrencyService(this);

        Currency existsCurrency = currencyService.getByCode(editCode.getText().toString());

        if (existsCurrency != null) return;

        existsCurrency = currencyService.getByName(editName.getText().toString());

        if (existsCurrency != null) return;

        Currency currency = new Currency();
        currency.setName(editName.getText().toString());
        currency.setCode(editCode.getText().toString());
        currency.setDate(new Date());
        currency.setValue(1);
        currencyService.add(currency);

        CurrencyUpdater updater = new CurrencyUpdater(this);
        updater.update(editName.getText().toString(), true);

        finish();
    }
}
