    package com.example.shop_app_test.presenter.ui.fragments

    import android.annotation.SuppressLint
    import android.app.AlertDialog
    import android.content.res.ColorStateList
    import android.graphics.Color
    import android.graphics.drawable.ColorDrawable
    import android.util.Log
    import android.view.View
    import android.view.animation.AccelerateDecelerateInterpolator
    import android.view.animation.Animation
    import android.view.animation.AnimationUtils
    import android.widget.Button
    import android.widget.ImageView
    import android.widget.RadioButton
    import android.widget.RatingBar
    import android.widget.TextView
    import androidx.core.content.ContextCompat
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsControllerCompat
    import androidx.fragment.app.viewModels
    import androidx.navigation.fragment.findNavController
    import by.kirich1409.viewbindingdelegate.viewBinding
    import com.example.core_urils.base.BaseFragment
    import com.example.core_urils.loadImageStr
    import com.example.core_urils.setupColorStateList
    import com.example.shop_app_test.R
    import com.example.shop_app_test.databinding.FragmentProductsBinding
    import com.example.shop_app_test.domain.model.Product
    import com.example.shop_app_test.domain.model.Rating
    import com.example.shop_app_test.presenter.ui.adapter.ProductsAdapter
    import com.example.shop_app_test.presenter.ui.pref.SharedPref
    import com.example.shop_app_test.presenter.ui.viewmodel.ProductsViewModel
    import com.google.android.material.bottomsheet.BottomSheetBehavior
    import com.google.android.material.bottomsheet.BottomSheetDialog
    import dagger.hilt.android.AndroidEntryPoint
    import java.lang.Math.abs

    @AndroidEntryPoint
    class ProductsFragment : BaseFragment(R.layout.fragment_products) {

        private val binding by viewBinding(FragmentProductsBinding::bind)
        private val viewModel by viewModels<ProductsViewModel>()
        private val adapter = ProductsAdapter(this::onClick)
        private lateinit var selectModel:Product
        private var alertDialog: AlertDialog? = null
        private var isCollapsed = false
        override fun initialize() {
            binding.rvProduct.adapter = adapter
            binding.imgShop.setOnClickListener {
                findNavController().navigate(R.id.action_productsFragment_to_basketFragment)
            }
            binding.imgCategory.setOnClickListener {
                alertDialog()
            }
            collapsingToolbarListener()
    }
        override fun initSubscribers() {
            viewModel.products.collectUIState(
                state = {},
                onSuccess = {
                    adapter.submitList(it)
                }
            )
            viewModel.loadAllProductRoom()
        }

        private fun onClick(model: Product) {
            showBottomSheetProduct(
                id = model.id,
                img = model.image,
                productName = model.title,
                category = model.category,
                rating = model.rating.rate,
                price = model.price,
                desc = model.description,
                title = model.title
            )
            selectModel = Product(
                id = model.id,
                title = model.title,
                category = model.category,
                rating = Rating(rate = model.rating.rate, count = 0),
                price = model.price,
                description = model.description,
                image = model.image,
                isSaved = false
            )
        }

        private fun showBottomSheetProduct(
            id: Int, img: String, productName: String, category: String, rating: Double,
            price: Double, desc: String, title:String
        ) {
            val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_product, null)
            val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(bottomSheetView)
            val imgProduct = bottomSheetView.findViewById<ImageView>(R.id.img_product)
            val nameProduct = bottomSheetView.findViewById<TextView>(R.id.tv_product_name)
            val nameCategory = bottomSheetView.findViewById<TextView>(R.id.tv_category_name)
            val ratingBar = bottomSheetView.findViewById<RatingBar>(R.id.rating_bar)
            val priceProduct = bottomSheetView.findViewById<TextView>(R.id.tv_price_product)
            val descProduct = bottomSheetView.findViewById<TextView>(R.id.tv_desc)
            val btnAdd = bottomSheetView.findViewById<Button>(R.id.btn_add_to_card)
            val btnGo = bottomSheetView.findViewById<Button>(R.id.btn_go_to_card)
            imgProduct.loadImageStr(img)
            nameProduct.text = productName
            nameCategory.text = category
            priceProduct.text = price.toString()
            descProduct.text = desc
            ratingBar.rating = rating.toFloat()

            ratingBar.numStars = 5

            btnAdd.setOnClickListener {
                btnAdd.visibility = View.GONE
                btnGo.visibility = View.VISIBLE

                val sharedPref = SharedPref(requireContext())
                val selectedProducts = sharedPref.loadSelectedProducts().toMutableList()

                val existingProduct = selectedProducts.find { it.id == selectModel.id }

                if (existingProduct == null) {
                    selectedProducts.add(selectModel)
                    sharedPref.saveSelectedProducts(selectedProducts)
                }
            }
            btnGo.setOnClickListener {
                findNavController().navigate(R.id.action_productsFragment_to_basketFragment)
                dialog.dismiss()
            }

            dialog.show()
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        }

        @SuppressLint("ResourceAsColor")
        private fun alertDialog() {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)

            val tvAccept = dialogView.findViewById<TextView>(R.id.tv_accept)
            val tvDecline = dialogView.findViewById<TextView>(R.id.tv_decline)
            val rbAll = dialogView.findViewById<RadioButton>(R.id.rb_all)
            val rbMensClock = dialogView.findViewById<RadioButton>(R.id.rb_mens_clock)
            val rbJewelry = dialogView.findViewById<RadioButton>(R.id.rb_jewerly)
            val rbElectronics = dialogView.findViewById<RadioButton>(R.id.rb_electronics)
            val rbWomenClock = dialogView.findViewById<RadioButton>(R.id.rb_women_clock)

            val radioButtons = listOf(rbAll, rbMensClock, rbJewelry, rbElectronics, rbWomenClock)
            radioButtons.forEach { radioButton ->
                radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        radioButtons.filter { it != radioButton }.forEach { it.isChecked = false }
                    }
                }
            }
            radioButtons.forEach { it.setupColorStateList() }

            tvAccept.setOnClickListener {
                val selectedCategory = when {
                    rbMensClock.isChecked -> "men's clothing"
                    rbJewelry.isChecked -> "jewelery"
                    rbElectronics.isChecked -> "electronics"
                    rbWomenClock.isChecked -> "women's clothing"
                    else -> "all"
                }
                viewModel.filterProducts(selectedCategory)
                alertDialog?.dismiss()
            }

            tvDecline.setOnClickListener {
                alertDialog?.dismiss()
            }

            builder.setView(dialogView)
            alertDialog = builder.create()
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.show()
        }

        private fun collapsingToolbarListener() = with(binding) {
            appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                val scrollRange = appBarLayout.totalScrollRange
                val alpha = 1.0f - abs(verticalOffset / scrollRange.toFloat())
                imgLogo.alpha = alpha

                if (abs(verticalOffset) >= scrollRange) {
                    activity?.window?.let { window ->
                        window.statusBarColor =
                            ContextCompat.getColor(requireContext(), R.color.grayAvarage)
                        WindowInsetsControllerCompat(
                            window,
                            window.decorView
                        ).isAppearanceLightStatusBars = false
                    }
                } else {
                    activity?.window?.let { window ->
                        window.statusBarColor =
                            ContextCompat.getColor(requireContext(), R.color.graylight)
                        WindowInsetsControllerCompat(
                            window,
                            window.decorView
                        ).isAppearanceLightStatusBars = true
                    }
                }
                val currentHeight = scrollRange + verticalOffset

                if (currentHeight < 152 && !isCollapsed) {
                    //centering tvCategory
                    isCollapsed = true

                    val translationX = products.width / 2 - tvCategory.width / 2

                    tvMovingAnimation(translationX.toFloat())
                } else if (currentHeight > 20 && isCollapsed) {
                    //moving to starting pos
                    isCollapsed = false

                    tvMovingAnimation(0f)
                }
            }
        }
        private fun tvMovingAnimation(sum: Float) = with(binding) {
            ViewCompat.animate(tvCategory)
                .translationX(sum)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }