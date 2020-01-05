package com.example.qzavyer.shoplist.Service;

import android.content.Context;

import com.example.qzavyer.shoplist.Models.Bill;
import com.example.qzavyer.shoplist.Models.Category;

import java.util.ArrayList;

public class CategoryService {
    private Context context;

    public CategoryService(Context context) {
        this.context = context;
    }

    public ArrayList<String> getNames() {
        CategoryRepository repository = new CategoryRepository(context);
        ArrayList<Category> categories = repository.all();

        ArrayList<String> names = new ArrayList<>(categories.size());

        for (Category category : categories) {
            names.add(category.getName());
        }

        return names;
    }
}

