package com.example.qzavyer.shoplist.Network;

import com.example.qzavyer.shoplist.Common.IUpdater;

public class UpdateCurrency {
    public void update(String currency, IUpdater updater) {
        String urlCurrencies = "";

        String url = "https://currate.ru/api/?get=rates&pairs=";

            urlCurrencies += currency + "RUB";

        url += urlCurrencies;
        url += "&key=9d9e3c67fceae6aca7d41086f9ffa95f";

        JsonTask task = new JsonTask(updater);
        task.execute(url);
    }
}