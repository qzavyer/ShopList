package com.example.qzavyer.shoplist.Service;

import android.content.Context;

import com.example.qzavyer.shoplist.Models.Currency;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Сервис управления валютами
 */
public class CurrencyService extends CommonService{
    public CurrencyService(Context context) {
        super(context);
    }

    public ArrayList<String> getNames(){
        CurrencyRepository repository = new CurrencyRepository(context);
        ArrayList<Currency> currencies = repository.all();

        ArrayList<String> names = new ArrayList<>(currencies.size());

        for (Currency currency : currencies) {
            names.add(currency.getName());
        }

        return names;
    }

    public Currency getByCode(String code){
        CurrencyRepository repository = new CurrencyRepository(context);

        code = code.toUpperCase();

        return repository.getByCode(code);
    }

    public Currency getByName(String name){
        name = capitalizeFirstLetter(name);

        CurrencyRepository repository = new CurrencyRepository(context);

        return repository.getByName(name);
    }

    public Currency update(@NotNull Currency currency){
        CurrencyRepository repository = new CurrencyRepository(context);

        currency.setName(capitalizeFirstLetter(currency.getName()));
        currency.setCode(currency.getCode().toUpperCase());

        return repository.update(currency);
    }

    public Currency add(Currency currency){
        CurrencyRepository repository = new CurrencyRepository(context);

        currency.setName(capitalizeFirstLetter(currency.getName()));
        currency.setCode(currency.getCode().toUpperCase());

        return repository.add(currency);
    }
}

