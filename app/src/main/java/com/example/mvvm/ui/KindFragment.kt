package com.example.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.base.KindDataBindingAdapter
import com.example.mvvm.data.Datas
import com.example.mvvm.data.WxData
import com.example.mvvm.databinding.ThreeLayoutBinding
import com.example.mvvm.net.ApiManager
import com.example.mvvm.net.WanNetApi
import jack.hive.HiveLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class KindFragment : Fragment() {
    companion object {
        private const val TAG = "ThreeFragment"
    }

    lateinit var bing: ThreeLayoutBinding
    lateinit var kindAdapter: KindDataBindingAdapter
    private var retrofit: Retrofit = ApiManager.getApiManager()!!.getBaseNet()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bing = ThreeLayoutBinding.inflate(inflater, container, false)
        return bing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")

        kindAdapter = KindDataBindingAdapter()
        bing.rwLayout.layoutManager =
            HiveLayoutManager(LinearLayoutManager.HORIZONTAL) as RecyclerView.LayoutManager
        bing.rwLayout.adapter = kindAdapter
        GlobalScope.launch(Dispatchers.Main) {
            val wxData = getWxData()
            kindAdapter.setList(wxData)
            runAnimation()
        }
    }

    private suspend fun getWxData(): List<Datas> {
        val data: WxData = withContext(Dispatchers.IO) {
            retrofit.create(WanNetApi::class.java).getWxList()
        }
        return data.data
    }

    private fun runAnimation() {
        val controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
        bing.rwLayout.layoutAnimation = controller
        kindAdapter.notifyDataSetChanged()
        bing.rwLayout.scheduleLayoutAnimation()
    }
}