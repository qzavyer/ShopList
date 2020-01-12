package com.example.qzavyer.shoplist;

import android.content.Context;

import com.example.qzavyer.shoplist.Common.IUpdater;
import com.example.qzavyer.shoplist.Models.Currency;
import com.example.qzavyer.shoplist.Network.UpdateCurrency;
import com.example.qzavyer.shoplist.Service.CurrencyService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CurrencyUpdater implements IUpdater {
    private Context context;
    private ArrayList<String> currencies = new ArrayList<>();

    CurrencyUpdater(Context context){
        this.context = context;
    }

    void update(String currencyName, boolean force){
        CurrencyService currencyService = new CurrencyService(context);
        Currency currency = currencyService.getByName(currencyName);

        if (currency.getCode().equals("RUB")) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date calDate = cal.getTime();

        if(force || currency.getDate().before(calDate)) {
            currencies.add(currency.getCode());

            UpdateCurrency updater = new UpdateCurrency();
            updater.update(currency.getCode(), this);
        }
    }

    @Override
    public void updateCurrency(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");

            CurrencyService currencyService = new CurrencyService(context);

            for (String code : currencies) {
                double value = data.getDouble(code + "RUB");

                Currency currency = currencyService.getByCode(code);

                currency.setDate(new Date());
                currency.setValue(value);

                currencyService.update(currency);
            }

            currencies.clear();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
