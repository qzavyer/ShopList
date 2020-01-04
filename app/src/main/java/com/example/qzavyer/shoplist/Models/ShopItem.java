package com.example.qzavyer.shoplist.Models;

/**
 * Модель элемента списка
 */
public class ShopItem {
    /**
     * Идентификатор
     */
    private int id;
    /**
     * Название товара
     */
    private String name;
    /**
     * Цена товара
     */
    private double price;
    /**
     * Количество
     */
    private double count;
    /**
     * Единица измерения
     */
    private String unit;
    /**
     * Отмечен
     */
    private boolean checked;
    /**
     * Идентификатор товара
     */
    private int goodId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }
}