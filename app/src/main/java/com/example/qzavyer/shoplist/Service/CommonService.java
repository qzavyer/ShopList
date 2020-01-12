package com.example.qzavyer.shoplist.Service;

import android.content.Context;

/**
 * Базовый класс сервиса
 */
class CommonService{
    /**
     * Контекст выполнения
     */
    protected Context context;

    CommonService(Context context){
        this.context = context;
    }

    /**
     * Возвращает текст с большой буквы
     * @param customText Исходный текст
     * @return Итоговый текст
     */
    static String capitalizeFirstLetter(@org.jetbrains.annotations.NotNull String customText) {
        int count = customText.length();
        if (count == 0) return customText;

        if (count == 1) return customText.toUpperCase();

        return customText.substring(0, 1).toUpperCase() + customText.substring(1).toLowerCase();
    }
}
