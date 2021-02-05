package com.example.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.mvvm.base.FavoritesDataBindingAdapter
import com.example.mvvm.databinding.OneLayoutBinding
import com.example.mvvm.model.FavoritesViewModel

class FavoritesFragment : Fragment() {
    companion object {
        private const val TAG = "OneFragment"
    }

    lateinit var bing: OneLayoutBinding
    lateinit var model: FavoritesViewModel
    private lateinit var pagerListQuicklyAdapter: FavoritesDataBindingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bing = OneLayoutBinding.inflate(inflater)
        /**
         * *ViewModel viewModel = new ViewModelProvider(getActivity()/this).get(*ViewModel.class);
         */
        model = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        bing.model = model
        bing.lifecycleOwner = this

        initUi()

        initModelObser()

        return bing.root
    }

    private fun initUi() {
        pagerListQuicklyAdapter = FavoritesDataBindingAdapter()
        bing.favoritesRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bing.favoritesRv.adapter = pagerListQuicklyAdapter
        bing.favoritesFresh.setOnRefreshListener {
            model.getFavoritesData()
        }
        pagerListQuicklyAdapter.loadMoreModule.run {
            isEnableLoadMore = true
            isAutoLoadMore = true
            enableLoadMoreEndClick = true

        }

        pagerListQuicklyAdapter.setOnItemClickListener { _, view, position ->
            val bundle = bundleOf("key" to pagerListQuicklyAdapter.data[position].link)
            Navigation.findNavController(view).navigate(R.id.webContentFragment2, bundle)
        }

        pagerListQuicklyAdapter.loadMoreModule.setOnLoadMoreListener {
            model.getMoreFavorites()
        }
    }

    private fun initModelObser() {
        model.listFavoriteData.observe(viewLifecycleOwner) {
            if (it == null || it.isEmpty()) {
                Toast.makeText(activity, "快去收藏吧大兄弟", Toast.LENGTH_SHORT).show()
            } else {
                pagerListQuicklyAdapter.setList(it)
                pagerListQuicklyAdapter.notifyDataSetChanged()
            }
        }

        model.loadMoreEnd.observe(viewLifecycleOwner, Observer {
            when (it) {
                1 -> {
                    pagerListQuicklyAdapter.loadMoreModule.loadMoreComplete()
                }
                2 -> {
                    pagerListQuicklyAdapter.loadMoreModule.loadMoreEnd(true)
                }
            }
        })

        model.favoritesDataMore.observe(viewLifecycleOwner, Observer {
            pagerListQuicklyAdapter.addData(it)
            pagerListQuicklyAdapter.notifyDataSetChanged()
        })

        model.freshCompleted.observe(viewLifecycleOwner, Observer {
            bing.favoritesFresh.isRefreshing = !it
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        model.getFavoritesData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
    }
}