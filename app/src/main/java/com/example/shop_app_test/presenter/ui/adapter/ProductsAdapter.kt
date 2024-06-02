package com.example.shop_app_test.presenter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_urils.loadImageStr
import com.example.shop_app_test.databinding.ItemShopBinding
import com.example.shop_app_test.domain.model.Product

class ProductsAdapter(val onClick:(Product)->Unit):ListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductDiff()) {

    inner class ProductViewHolder(private val binding:ItemShopBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Product) {
            binding.imgProduct.loadImageStr(model.image)
            binding.tvDescProduct.text = model.title
            binding.tvProductPrice.text = "${model.price} $"

            itemView.setOnClickListener {
                onClick(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProductDiff:DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}
