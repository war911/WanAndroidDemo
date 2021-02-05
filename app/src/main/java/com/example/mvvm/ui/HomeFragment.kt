package com.example.mvvm.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.base.HomeDataBindingAdapter
import com.example.mvvm.data.MainBanner
import com.example.mvvm.databinding.HomeLayoutBinding
import com.example.mvvm.model.HomeViewModel
import com.example.mvvm.utils.GlideImageLoader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.coroutines.delay


class HomeFragment : Fragment() {
    companion object {
        private const val TAG = "HomeFragment"
    }


    lateinit var bing: HomeLayoutBinding
    lateinit var model: HomeViewModel
    private val banner by lazy { Banner(activity) }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    private lateinit var pagerListQuicklyAdapter: HomeDataBindingAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        bing = HomeLayoutBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(HomeViewModel::class.java)
        bing.homeModel = model
        bing.lifecycleOwner = this

        model.showView.value = true
        bing.swLayout.setOnRefreshListener {
            model.showViewTop.postValue(true)
            model.getMainData()
        }

        bing.swLayout.isRefreshing = model.showView.value!!

        model.showViewTop.observe(viewLifecycleOwner) {
            Log.d(TAG, "onCreateView: change 停下来 $it")
            bing.swLayout.isRefreshing = it
        }
        return bing.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")

        bing.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        pagerListQuicklyAdapter = HomeDataBindingAdapter()

        bing.recyclerView.adapter = pagerListQuicklyAdapter

        /**
         * 恢复界面的时候滚动到顶部，如不需要就注释掉。
         */
        bing.recyclerView.post(Runnable { bing.recyclerView.scrollToPosition(0) })

        pagerListQuicklyAdapter.loadMoreModule.run {
            isEnableLoadMore = true
            isAutoLoadMore = true
            enableLoadMoreEndClick = true

        }

        pagerListQuicklyAdapter.loadMoreModule.setOnLoadMoreListener {
            model.getMoreMainData()
        }

        model.loadMoreEnd.observe(this, Observer {
            when (it) {
                1 -> {
                    pagerListQuicklyAdapter.loadMoreModule.loadMoreComplete()
                }
                2 -> {
                    pagerListQuicklyAdapter.loadMoreModule.loadMoreEnd(true)
                }
            }

        })

        initBanner()

        pagerListQuicklyAdapter.addHeaderView(banner)
        pagerListQuicklyAdapter.addChildClickViewIds(R.id.conHomeLayout, R.id.homeShine)
        pagerListQuicklyAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.homeShine) {
                pagerListQuicklyAdapter.data[position].let {
                    if (it.collect) {
                        model.unCollect(it.id)
                        it.collect = false
                    } else {
                        model.collect(it.id, object : HomeViewModel.CallBack {
                            override fun onSuccess() {
                                it.collect = true
                            }

                            override fun onFail() {
                                Toast.makeText(activity, "先登录啊大哥", Toast.LENGTH_SHORT).show()

                            }
                        })
                    }
                }
            } else {
                val bundle = bundleOf("key" to pagerListQuicklyAdapter.data[position].link)
                Navigation.findNavController(view).navigate(R.id.webContentFragment2, bundle)
            }
        }

        model.mainPageData.observe(this, Observer {
            it?.let {
                pagerListQuicklyAdapter.setList(it.data!!.datas!!)
                pagerListQuicklyAdapter.notifyDataSetChanged()
            }
        })


        model.mainPageDataMore.observe(this, Observer {
            it?.let {
                pagerListQuicklyAdapter.addData(it.data!!.datas!!)
                pagerListQuicklyAdapter.notifyDataSetChanged()
            }
        })


        model.mainBannerData.observe(this,
            Observer { t ->
                Log.d(TAG, "onChanged: ${t?.getData()}")

                Log.d(TAG, "onViewCreated: ${bing.homeModel?.mainBannerData?.value}")
                t?.let {
                    setBanner(t)
                }
            })

    }


    private fun initBanner() {
        val wm = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width //屏幕宽度
        banner.run {
            layoutParams =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    width / 16 * 9
                )
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(GlideImageLoader())
            setOnBannerListener { position ->
                run {
                    Log.d(TAG, "initBanner: 点击了$position")
                }
            }
        }
    }


    private fun setBanner(bannerList: MainBanner) {
        bannerList.getData()?.forEach { it ->
            bannerImages.add(it.imagePath!!)
            bannerTitles.add(it.title!!)
            bannerUrls.add(it.url!!)
        }

        banner.setImages(bannerImages)
            .setBannerTitles(bannerTitles)
            .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            .setDelayTime(3000)
        banner.start()
        banner.setOnBannerListener {
            val bundle = bundleOf("key" to bannerUrls[it])
            Navigation.findNavController(banner).navigate(R.id.webContentFragment2, bundle)
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        model.getMainData()
    }


    suspend fun one(): Int {
        delay(1500)
        return 1
    }

    suspend fun two(): Int {
        delay(1500)
        return 2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
        pagerListQuicklyAdapter.removeHeaderView(banner)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        model.showView.value = false
    }


}