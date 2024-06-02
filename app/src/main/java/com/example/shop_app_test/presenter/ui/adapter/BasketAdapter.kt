import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_urils.loadImageStr
import com.example.shop_app_test.domain.model.Product
import com.example.shop_app_test.databinding.ItemBasketBinding

class BasketAdapter : ListAdapter<Product, BasketAdapter.BasketViewHolder>(ProductDiffCallback()) {

    inner class BasketViewHolder(private val binding:ItemBasketBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.imgProduct.loadImageStr(product.image)
            binding.tvTitleProduct.text = product.title
            binding.tvPriceProduct.text = product.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
