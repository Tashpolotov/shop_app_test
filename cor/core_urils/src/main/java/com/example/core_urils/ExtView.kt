package com.example.core_urils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import com.bumptech.glide.Glide

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(image: Int) {
    Glide.with(this).load(image).into(this)
}
fun ImageView.loadImageStr(image: String) {
    Glide.with(this).load(image).into(this)
}
fun RadioButton.setupColorStateList() {
    if (Build.VERSION.SDK_INT >= 21) {
        // Цвет для выбранного состояния
        val selectedColor = Color.parseColor("#FF000000")

        // Цвет для не выбранного состояния
        val unselectedColor = Color.parseColor("#555555")

        // Создаем ColorStateList
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
            ),
            intArrayOf(
                unselectedColor, // Не выбран
                selectedColor // Выбран
            )
        )

        // Применяем ColorStateList к RadioButton
        buttonTintList = colorStateList
    }
}


