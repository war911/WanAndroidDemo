package com.example.mvvm.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.MainPageData
import com.example.mvvm.databinding.HomeListItemBinding


class HomePagerListAdapter(list: MainPageData.DataBean,click:ListOnClick) :
    RecyclerView.Adapter<HomePagerListAdapter.ViewHolder>() {
    companion object{
        private const val TAG = "HomePagerListAdapter"
    }
    lateinit var bing: HomeListItemBinding
    var mList:MainPageData.DataBean = list
    var mClick:ListOnClick = click



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        bing = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.home_list_item, parent, false
        )
        return ViewHolder(bing)
    }


    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${mList.size}")
        return mList.size
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            mClick.onClick(mList.datas?.get(position)?.link)
        }
        bing.data = mList.datas?.get(position)
        bing.tvTest.text = "第 $position"
        holder.binding?.executePendingBindings()
    }

    class ViewHolder(itemView: ViewDataBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding: ViewDataBinding? = null

        init {
            binding = itemView
        }
    }


    interface ListOnClick {
        fun onClick(url:String?)
    }
}