package com.example.qzavyer.shoplist.Service;

import android.content.Context;

import com.example.qzavyer.shoplist.Models.Bill;

import java.util.ArrayList;

public class BillService{
    private Context context;

    public BillService(Context context) {
        this.context = context;
    }

    public ArrayList<Bill> add(ArrayList<Bill> bills){
        BillRepository repository = new BillRepository(context);

        return repository.add(bills);
    }
}
