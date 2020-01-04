package com.example.qzavyer.shoplist.Models;

/**
 * Модель товара
 */
public class Good {
    /**
     * Идентификатор
     */
    private int id;
    /**
     * Название
     */
    private String name;

    /**
     * Единица измерения
     */
    private String unit;

    /**
     * Цена
     */
    private double price;

    /**
     * Идентификатор сатегории
     */
    private int categoryId;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}