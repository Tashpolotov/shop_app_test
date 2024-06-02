package com.example.shop_app_test.presenter.ui.fragments

import BasketAdapter
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_urils.base.BaseFragment
import com.example.shop_app_test.R
import com.example.shop_app_test.databinding.FragmentBasketBinding
import com.example.shop_app_test.domain.model.Product
import com.example.shop_app_test.presenter.ui.pref.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : BaseFragment(R.layout.fragment_basket) {

    private val binding by viewBinding(FragmentBasketBinding::bind)
    private val adapter = BasketAdapter()
    private lateinit var sharedPref: SharedPref

    override fun initialize() {
        binding.rvBasket.adapter = adapter
        sharedPref = SharedPref(requireContext())

        val selectedProducts = sharedPref.loadSelectedProducts()
        if (selectedProducts.isEmpty()) {
            binding.btnAddSomething.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.VISIBLE
            binding.btnBuy.visibility = View.GONE
        } else {
            binding.btnAddSomething.visibility = View.GONE
            binding.tvEmpty.visibility = View.GONE
            binding.btnBuy.visibility = View.VISIBLE
        }

        binding.btnAddSomething.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnBuy.setOnClickListener {
            sharedPref.clearSharedPrefs()
            updateUI(emptyList())
            showBasketContent(selectedProducts)
        }
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initSubscribers() {
        val selectedProducts = sharedPref.loadSelectedProducts()
        adapter.submitList(selectedProducts)

        if (selectedProducts.isEmpty()) {
            binding.btnAddSomething.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.VISIBLE
            binding.btnBuy.visibility = View.GONE
        } else {
            binding.btnAddSomething.visibility = View.GONE
            binding.tvEmpty.visibility = View.GONE
            binding.btnBuy.visibility = View.VISIBLE
        }
    }
    private fun updateUI(selectedProducts: List<Product>) {
        if (selectedProducts.isEmpty()) {
            binding.btnAddSomething.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.VISIBLE
            binding.btnBuy.visibility = View.GONE
        } else {
            binding.btnAddSomething.visibility = View.GONE
            binding.tvEmpty.visibility = View.GONE
            binding.btnBuy.visibility = View.VISIBLE
        }
        adapter.submitList(selectedProducts)
    }
    private fun showBasketContent(selectedProducts: List<Product>) {
        val basketContent = StringBuilder()
        selectedProducts.forEachIndexed { index, product ->
            basketContent.append("${index + 1}. ${product.title} - ${product.price}\n")
        }
        Toast.makeText(requireContext(), "Корзина:\n$basketContent", Toast.LENGTH_LONG).show()
    }
}