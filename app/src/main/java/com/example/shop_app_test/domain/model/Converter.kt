package com.example.shop_app_test.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun fromRating(rating: Rating): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    fun toRating(ratingString: String): Rating {
        return gson.fromJson(ratingString, Rating::class.java)
    }

}