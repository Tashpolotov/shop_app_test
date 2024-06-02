package com.example.shop_app_test.presenter.ui.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core_urils.base.BaseFragment
import com.example.shop_app_test.R
import com.example.shop_app_test.presenter.ui.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplahScreenFragment : BaseFragment(R.layout.fragment_splah_screen) {

    private var delayJob: Job? = null
    private val viewModel by viewModels<ProductsViewModel>()

    override fun initialize() {
        delayJob = lifecycleScope.launch {
            delay(3000)
            findNavController().navigate(R.id.productsFragment)
        }

        viewModel.products.collectUIState(
            state = {
            },
            onSuccess = { products ->
                viewModel.insertProducts(products)
            }
        )
        viewModel.loadAllProducts()
    }

    override fun onStop() {
        super.onStop()
        delayJob?.cancel()

    }
}