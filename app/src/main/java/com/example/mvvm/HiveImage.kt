package com.example.mvvm

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.BindingAdapter
import com.sackcentury.shinebuttonlib.ShineButton
import jack.hive.HiveDrawable
import jack.hive.HiveLayoutManager

class HiveImage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        val colorDrawable = ColorDrawable(
            context.resources.getColor(R.color.colorAccent))
        val toBitmap = colorDrawable.toBitmap(100, 100, null)
        val hiveDrawable = HiveDrawable(HiveLayoutManager.HORIZONTAL, toBitmap)
        setImageDrawable(hiveDrawable)
    }




}