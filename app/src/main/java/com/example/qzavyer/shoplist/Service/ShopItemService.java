package com.example.qzavyer.shoplist.Service;

import android.content.ContentValues;
import android.content.Context;

import com.example.qzavyer.shoplist.Models.Category;
import com.example.qzavyer.shoplist.Models.Good;
import com.example.qzavyer.shoplist.Models.ShopItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShopItemService extends CommonService {
    public ShopItemService(Context context) {
        super(context);
    }

    public ShopItem add(@NotNull ShopItem item, Category category) {
        String name = capitalizeFirstLetter(item.getName());

        GoodsRepository goodsRepository = new GoodsRepository(context);

        Good good = goodsRepository.getGoodByName(name);

        if (good == null) {
            good = new Good();
            good.setName(name);
            good.setPrice(item.getPrice());
            good.setUnit(item.getUnit().toLowerCase());

            String categoryName = capitalizeFirstLetter(category.getName());
            CategoryRepository categoryRepository = new CategoryRepository(context);

            Category cat = categoryRepository.getByName(categoryName);

            if (cat == null) {
                cat = new Category();
                cat.setName(categoryName);

                cat = categoryRepository.add(cat);
            }

            good.setCategoryId(cat.getId());

            // создаем объект для данных
            ContentValues cv = new ContentValues();

            cv.put("name", good.getName());
            cv.put("price", good.getPrice() * 100);
            cv.put("unit", good.getUnit());
            cv.put("categoryId", good.getCategoryId());

            good = goodsRepository.add(good);
        }

        item.setGoodId(good.getId());

        ShopItemsRepository itemsRepository = new ShopItemsRepository(context);
        item = itemsRepository.add(item);

        return item;
    }

    public void deleteAll() {
        ShopItemsRepository itemsRepository = new ShopItemsRepository(context);

        itemsRepository.deleteAll();
    }

    public void deleteAllChecked() {
        ShopItemsRepository itemsRepository = new ShopItemsRepository(context);

        itemsRepository.deleteAllChecked();
    }

    public ArrayList<ShopItem> all() {
        ShopItemsRepository itemsRepository = new ShopItemsRepository(context);

        return itemsRepository.all();
    }

    public void setChecked(int id, boolean checked) {
        ShopItemsRepository itemsRepository = new ShopItemsRepository(context);
        itemsRepository.setChecked(id, checked);
    }

    public ArrayList<ShopItem> allChecked(){
        ShopItemsRepository itemsRepository = new ShopItemsRepository(context);

        return itemsRepository.allChecked();
    }

    public ShopItem getById(int id) {
        ShopItemsRepository itemsRepository = new ShopItemsRepository(context);

        return itemsRepository.getItemById(id);
    }
}