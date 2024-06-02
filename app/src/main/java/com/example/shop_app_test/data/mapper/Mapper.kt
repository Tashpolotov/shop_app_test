package com.example.shop_app_test.data.mapper

import com.example.shop_app_test.data.model.ProductDTO
import com.example.shop_app_test.data.model.RatingDTO
import com.example.shop_app_test.domain.model.Product
import com.example.shop_app_test.domain.model.Rating

fun ProductDTO.toProduct() = Product(
    id, title, price, description, category, image, rating.toRating()
)

fun RatingDTO.toRating()= Rating(
    rate, count
)
