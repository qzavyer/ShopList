package com.example.qzavyer.shoplist.Models;

import java.util.Date;

public class Bill {
    /**
     * Идентификатор
     */
    private int id;

    /**
     * Идентификатор товара
     */
    private int goodId;

    /**
     * Идентификатор магазина
     */
    private int shopId;
    /**
     * Дата покупки
     */
    private Date date;

    /**
     * Количество
     */
    private double count;

    /**
     * Цена
     */
    private double price;

    /**
     * Сумма
     */
    private double sum;

    /**
     * Валюта
     */
    private String currency;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}