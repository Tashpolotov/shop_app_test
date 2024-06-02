package com.example.shop_app_test.data.model

import androidx.room.Embedded

data class ProductDTO(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    @Embedded
    val rating: RatingDTO,
    val isSaved: Boolean = false
)
