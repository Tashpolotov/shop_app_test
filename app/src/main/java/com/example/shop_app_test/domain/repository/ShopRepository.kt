package com.example.shop_app_test.domain.repository


import com.example.core_urils.Resource
import com.example.shop_app_test.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ShopRepository {

    suspend fun getAllProducts(): Flow<Resource<List<Product>>>
    suspend fun getAllProductsFromDb(): List<Product>
    suspend fun insertProducts(products: List<Product>)
}