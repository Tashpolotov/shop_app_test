package com.example.shop_app_test.presenter.ui.pref

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.shop_app_test.domain.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveSelectedProducts(products: List<Product>) {
        val gson = Gson()
        val json = gson.toJson(products)
        sharedPreferences.edit().putString(Constants.SELECTED_PRODUCTS_KEY, json).apply()
    }

    fun loadSelectedProducts(): List<Product> {
        val gson = Gson()
        val json = sharedPreferences.getString(Constants.SELECTED_PRODUCTS_KEY, null)
        val type = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun clearSharedPrefs() {
        sharedPreferences.edit().clear().apply()
    }




    companion object {
        private const val PREF_NAME = "MyPrefs"
    }




}


