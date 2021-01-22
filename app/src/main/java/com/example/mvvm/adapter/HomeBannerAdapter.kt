package com.example.mvvm.adapter

import android.content.Context
import android.util.Log
import com.example.mvvm.R
import com.example.mvvm.data.MainBanner
import com.example.mvvm.databinding.HomeBannerItemBinding

class HomeBannerAdapter(context: Context?, dataList: List<MainBanner.DataBean>?) :
    CommonAdapter<HomeBannerItemBinding, MainBanner.DataBean>(context, dataList) {
    companion object {
        private const val TAG = "HomeBannerAdapter"
    }

    override fun getLayoutId(): Int {
        Log.d(Companion.TAG, "getLayoutId: ")
        return R.layout.home_banner_item
    }

    override fun bindView(viewHolder: CommonViewHolder?, position: Int) {
        Log.d(TAG, "bindView: $dataList")
        viewHolder?.bindView?.data = dataList[position]
    }


}