package com.example.shop_app_test.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
data class Product(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "rating")
    val rating: Rating,
    val isSaved: Boolean = false
):Serializable
