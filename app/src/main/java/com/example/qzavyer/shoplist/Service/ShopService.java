package com.example.qzavyer.shoplist.Service;

import android.content.Context;

import com.example.qzavyer.shoplist.Models.Shop;

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

    public Shop getShopByName(String name) {
        name = capitalizeFirstLetter(name);

        ShopsRepository repository = new ShopsRepository(context);
        Shop shop = repository.getShopByName(name);

        return shop;
    }

    public Shop add (Shop shop) {
        shop.setName(capitalizeFirstLetter(shop.getName()));

        ShopsRepository repository = new ShopsRepository(context);
        return repository.add(shop);
    }

    private static String capitalizeFirstLetter(@org.jetbrains.annotations.NotNull String customText) {
        int count = customText.length();
        if (count == 0) return customText;

        if (count == 1) return customText.toUpperCase();

        return customText.substring(0, 1).toUpperCase() + customText.substring(1).toLowerCase();
    }
}