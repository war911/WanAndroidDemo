package com.example.mvvm.model

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.FavoritesData
import com.example.mvvm.data.MainPageData
import com.example.mvvm.net.ApiManager
import com.example.mvvm.net.WanNetApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "FavoritesViewModel"
    }

    private var _listData = MutableLiveData<List<FavoritesData.DataBean.DatasBean>>()
    val listFavoriteData = _listData
    private var retrofit: Retrofit = ApiManager.getApiManager()!!.getBaseNet()
    var page = 0;

    private val _favoritesDataMore: MutableLiveData<List<FavoritesData.DataBean.DatasBean>> = MutableLiveData()
    val favoritesDataMore = _favoritesDataMore
    val _loadMoreEnd: MutableLiveData<Int> = MutableLiveData(0)
    val loadMoreEnd = _loadMoreEnd

    private val _freshCompleted = MutableLiveData<Boolean>(false)
    val freshCompleted = _freshCompleted

    init {
        retrofit = ApiManager.getApiManager()!!.getBaseNet()
    }


    @SuppressLint("CheckResult")
    fun getFavoritesData() {
        page = 0
        _freshCompleted.value = false
        retrofit.create(WanNetApi::class.java).getFavorites(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.errorCode == 0) {
                    _listData.value = it?.data?.datas
                } else {
                    Log.d(TAG, "getFavoritesData: ${it.toString()}")
                }
                _freshCompleted.value = true

            }, {
                Log.d(TAG, "getFavoritesData: 失败${it}")
                _freshCompleted.value = true
            })

    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getMoreFavorites() {
        _freshCompleted.value = false
        page++
        retrofit.create(WanNetApi::class.java).getFavorites(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "getMoreFavorites : 数据${it.toString()}")
                if (it.errorCode == 0) {
                    it?.data?.datas?.let { it ->
                        _favoritesDataMore.value = it
                        _loadMoreEnd.value = 1
                        _freshCompleted.value = true
                        retrofit
                    }
                    Toast.makeText(getApplication(), "getMoreFavorites 错误", Toast.LENGTH_SHORT).show()
                    _loadMoreEnd.value = 2
                    _freshCompleted.value = true
                    page--
                } else {
                    Toast.makeText(getApplication(), "错误", Toast.LENGTH_SHORT).show()
                    _loadMoreEnd.value = 2
                    _freshCompleted.value = true
                    page--
                }
            }, {
                Log.d(TAG, "getMoreFavorites : 失败${it}")
                _loadMoreEnd.value = 2
                _freshCompleted.value = true
                page--
            })
    }
}