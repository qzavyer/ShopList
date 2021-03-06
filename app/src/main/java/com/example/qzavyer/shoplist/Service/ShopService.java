package com.example.qzavyer.shoplist.Service;

import android.content.Context;

import com.example.qzavyer.shoplist.Models.Shop;

import java.util.ArrayList;

public class ShopService extends CommonService {
    public ShopService(Context context) {
        super(context);
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

        return repository.getShopByName(name);
    }

    public Shop getById(int id) {
        ShopsRepository repository = new ShopsRepository(context);

        return repository.getById(id);
    }

    public Shop add (Shop shop) {
        shop.setName(capitalizeFirstLetter(shop.getName()));

        ShopsRepository repository = new ShopsRepository(context);
        return repository.add(shop);
    }
}