package com.example.qzavyer.shoplist.Service;

import android.content.Context;

import com.example.qzavyer.shoplist.Models.Shop;
import com.example.qzavyer.shoplist.Service.Database.ShopsRepository;

import java.util.ArrayList;

public class ShopService {
    private Context context;

    public ShopService(Context context) {
        this.context = context;
    }

    public ArrayList<String> getShopNames() {
        ShopsRepository repository = new ShopsRepository(context);
        ArrayList<Shop> shops = repository.allShops();

        ArrayList<String> shopNames = new ArrayList<>(shops.size());

        for (Shop shop : shops) {
            shopNames.add(shop.getName());
        }

        return shopNames;
    }
}