package com.example.mvvm.base

import android.graphics.drawable.ColorDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvm.R
import com.example.mvvm.data.Datas
import com.example.mvvm.databinding.KindItemBinding
import jack.hive.HiveDrawable
import jack.hive.HiveLayoutManager

class KindDataBindingAdapter : BaseQuickAdapter<Datas, BaseViewHolder>(R.layout.kind_item),
    LoadMoreModule {
    override fun onItemViewHolderCreated(
        viewHolder: BaseViewHolder,
        viewType: Int
    ) {
        // 绑定 view
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: Datas) {
        item?.let {
            // 获取 Binding
            val binding: KindItemBinding? = holder.getBinding()
            if (binding != null) {
                // 设置数据
                binding.data = item
                binding.executePendingBindings()
            }
        }
    }

}