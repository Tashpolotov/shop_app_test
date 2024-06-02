package com.example.shop_app_test.domain.usecase

import com.example.shop_app_test.domain.repository.ShopRepository
import javax.inject.Inject

class ShopUseCase @Inject constructor(private val repository: ShopRepository) {

    suspend fun getAllProduct() = repository.getAllProducts()

}