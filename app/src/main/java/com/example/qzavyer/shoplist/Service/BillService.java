package com.example.qzavyer.shoplist.Service;

import android.content.Context;

import com.example.qzavyer.shoplist.Models.Bill;

import java.util.ArrayList;

public class BillService extends CommonService{
    public BillService(Context context) {
        super(context);
    }

    public ArrayList<Bill> add(ArrayList<Bill> bills){
        BillRepository repository = new BillRepository(context);

        return repository.add(bills);
    }

    /**
     * Возвращает код последней используеой валюты
     * @return Код валюты
     */
    public String getLastCurrency(){
        BillRepository repository = new BillRepository(context);

        Bill lastBill = repository.getLast();

        if(lastBill == null) return "RUB";

        return lastBill.getCurrency();
    }

    public Bill allLastByGood(int goodId) {
        BillRepository repository = new BillRepository(context);

        double sum = 0;
        double minPrice = 0;
        int shopId = 0;

        Bill newBill = new Bill();

        ArrayList<Bill> lastBills = repository.allLastByGood(goodId);
        if(lastBills.size()==0) return newBill;

        for(Bill bill : lastBills){
            double price = bill.getPrice();

            if (minPrice>price || minPrice == 0){
                minPrice = price;
                shopId = bill.getShopId();
            }

            sum += price;
        }

        newBill.setShopId(shopId);
        newBill.setPrice(sum/lastBills.size());

        return newBill;
    }
}

