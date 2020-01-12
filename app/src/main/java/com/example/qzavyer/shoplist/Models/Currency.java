package com.example.qzavyer.shoplist.Models;

import java.util.Date;

/**
 * Модель валюты
 */
public class Currency{
    /**
     * Код
     */
    private String code;

    /**
     * Наименование
     */
    private String name;

    /**
     * Цена в рублях
     */
    private double value;

    /**
     * Дата обновления
     */
    private Date date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
