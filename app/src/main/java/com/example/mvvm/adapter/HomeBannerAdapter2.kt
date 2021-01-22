package com.example.mvvm.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm.R
import com.example.mvvm.data.MainBanner


class HomeBannerAdapter2 : RecyclerView.Adapter<HomeBannerAdapter2.ViewHolder>() {

    private  var mList: List<MainBanner.DataBean> = ArrayList<MainBanner.DataBean>()
    private lateinit var mContext: Context
    var mOnClick:ListOnClick? = null
    companion object {
        private const val TAG = "HomeBannerAdapter2"
    }

    class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var myTextView: TextView = itemView.findViewById(R.id.tv_home_banner)
        var mImg: ImageView = itemView.findViewById(R.id.img_home_banner)
    }

    fun setData(context :Context,list:List<MainBanner.DataBean>){
        mList = list;
        mContext = context;

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.home_banner_item,parent,false)
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = lp
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if(mList==null)0 else mList.size
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.itemView.setOnClickListener {
//            mOnClick?.onClick(mList[position].url)
//        }
        holder.myTextView.text = mList[position].title
        Glide.with(mContext).load(mList[position].imagePath)
            .error(Color.WHITE)
            .into(holder.mImg)
    }

    interface ListOnClick {
        fun onClick(url:String?)
    }
}