package com.example.qzavyer.shoplist.Service;

import android.content.Context;

import com.example.qzavyer.shoplist.Models.Good;

import java.util.ArrayList;

public class GoodService {
    private Context context;

    public GoodService(Context context) {
        this.context = context;
    }

    public ArrayList<String> getGoodNames() {
        GoodsRepository repository = new GoodsRepository(context);
        ArrayList<Good> goods = repository.allGoods();

        ArrayList<String> goodNames = new ArrayList<>(goods.size());

        for (Good good : goods) {
            goodNames.add(good.getName());
        }

        return goodNames;
    }

    public ArrayList<String> getUnits() {
        GoodsRepository repository = new GoodsRepository(context);
        ArrayList<Good> goods = repository.allGoods();

        ArrayList<String> goodUnits = new ArrayList<>(goods.size());

        for (Good good : goods) {
            if (goodUnits.contains(good.getUnit())) continue;

            goodUnits.add(good.getUnit());
        }

        return goodUnits;
    }
}