package com.example.shop_app_test.data.repository


import com.example.core_urils.base.BaseRepository
import com.example.shop_app_test.data.mapper.toProduct
import com.example.shop_app_test.data.remote.ShopApiService
import com.example.shop_app_test.data.room.ProductDao
import com.example.shop_app_test.domain.model.Product
import com.example.shop_app_test.domain.repository.ShopRepository


class ShopRepositoryImpl(private val apiService: ShopApiService,
                         private val productDao: ProductDao,

    ): BaseRepository(), ShopRepository {

    override suspend fun getAllProducts() = listRequest{
        apiService.getAllProducts().map { it.toProduct() }
    }


    override suspend fun getAllProductsFromDb(): List<Product> {
        return productDao.getAllProducts()
    }

    override suspend fun insertProducts(products: List<Product>) {
        productDao.insertAll(products)
    }
}