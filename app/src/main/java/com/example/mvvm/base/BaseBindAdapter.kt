//package com.example.mvvm.base
//
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.chad.library.adapter.base.BaseViewHolder
//import com.chad.library.adapter.base.loadmore.LoadMoreView
//import com.example.mvvm.BR
//import com.example.mvvm.R
//
//open class BaseBindAdapter<T> (layoutResId: Int, br: Int) :BaseQuickAdapter<T,BaseBindAdapter.BindViewHolder>(layoutResId){
//    private val _br: Int = br
//
//
//
//    override fun convert(helper: BindViewHolder, item: T) {
//        helper.binding.run {
//            setVariable(BR.data, item)
//            executePendingBindings()
//        }
//    }
//
//    class BindViewHolder(view: View) : BaseViewHolder(view){
//        val binding:ViewDataBinding
//            get() = itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
//    }
//
//
//    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
//        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
//            ?: return super.getItemView(layoutResId, parent)
//        return binding.root.apply {
//            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
//        }
//    }
//}