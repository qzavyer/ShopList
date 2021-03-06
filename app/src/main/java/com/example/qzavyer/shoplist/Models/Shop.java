package com.example.qzavyer.shoplist.Models;

/**
 * Модель магазина
 */
public class Shop {
    /**
     * Идентификатор
     */
    private int id;

    /**
     * Название
     */
    private String name;

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
}