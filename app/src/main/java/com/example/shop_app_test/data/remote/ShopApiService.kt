package com.example.shop_app_test.data.remote

import com.example.shop_app_test.data.model.ProductDTO
import retrofit2.http.GET

interface ShopApiService {

    @GET("products")
    suspend fun getAllProducts():List<ProductDTO>
}