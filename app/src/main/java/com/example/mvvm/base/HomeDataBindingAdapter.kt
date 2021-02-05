package com.example.mvvm.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvm.R
import com.example.mvvm.data.MainPageData.DataBean.DatasBean
import com.example.mvvm.databinding.HomeListItemBinding

/**
 * @author YCKJ3655
 */
class HomeDataBindingAdapter :
    BaseQuickAdapter<DatasBean, BaseViewHolder>(R.layout.home_list_item),LoadMoreModule {
    /**
     * 当 ViewHolder 创建完毕以后，会执行此回掉
     * 可以在这里做任何你想做的事情
     */
    override fun onItemViewHolderCreated(
        viewHolder: BaseViewHolder,
        viewType: Int
    ) {
        // 绑定 view
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: DatasBean) {
        item?.let {
            // 获取 Binding
            val binding: HomeListItemBinding? = helper.getBinding()
            if (binding != null) {
                // 设置数据
                binding.data = item
                binding.executePendingBindings()
            }
        }
    }
}