package com.example.shop_app_test.presenter.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.core_urils.Resource
import com.example.core_urils.base.BaseViewModel
import com.example.shop_app_test.data.room.ProductDao
import com.example.shop_app_test.domain.model.Product
import com.example.shop_app_test.domain.usecase.ShopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val useCase: ShopUseCase,
    private val productDao: ProductDao,
) : BaseViewModel() {

    private val _products = MutableStateFlow<Resource<List<Product>>>(Resource.Empty())
    val products = _products.asStateFlow()

    private var allProducts: List<Product> = listOf()

    fun loadAllProducts() {
        viewModelScope.launch {
            useCase.getAllProduct().collectData(_products)
        }
    }

    fun insertProducts(products: List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.insertAll(products)
        }
    }

    fun loadAllProductRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val roomProducts = productDao.getAllProducts()
            allProducts = roomProducts
            _products.value = Resource.Success(roomProducts)
        }
    }

    fun filterProducts(category: String) {
        val filteredProducts = if (category == "all") {
            allProducts
        } else {
            allProducts.filter { it.category == category }
        }
        _products.value = Resource.Success(filteredProducts)
    }
}

