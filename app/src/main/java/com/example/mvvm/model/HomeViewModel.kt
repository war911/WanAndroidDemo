package com.example.mvvm.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.MainBanner
import com.example.mvvm.data.MainPageData
import com.example.mvvm.net.ApiManager
import com.example.mvvm.net.WanNetApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private var viewModelJob = SupervisorJob()
    private var pageCount = 0

    private var retrofit: Retrofit = ApiManager.getApiManager()!!.getBaseNet()
    val mainBannerData: MutableLiveData<MainBanner> = MutableLiveData()
    val mainPageData: MutableLiveData<MainPageData> = MutableLiveData()
    val mainPageDataMore: MutableLiveData<MainPageData> = MutableLiveData()
    val loadMoreEnd: MutableLiveData<Int> = MutableLiveData(0)
    var showView: MutableLiveData<Boolean> = MutableLiveData(false)
    var showViewTop: MutableLiveData<Boolean> = MutableLiveData(false)


    /**
     * RxJava 并行网络请求 zip 同事发网络请求，都有结果后回调。
     */
    fun getMainData() {
        val mainPageData1 = retrofit.create(WanNetApi::class.java).getMainPageData(pageCount)
            .subscribeOn(Schedulers.io())
        val mainBanner =
            retrofit.create(WanNetApi::class.java).getMainBanner().subscribeOn(Schedulers.io())

        Observable.zip(
            mainPageData1,
            mainBanner,
            BiFunction<MainPageData, MainBanner, String> { t1, t2 ->
                mainPageData.postValue(t1)
                mainBannerData.postValue(t2)
                Log.d(TAG, "getMainData: 完事了")
                "ok"
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "getMainData: 请求全部结束！！！！！！！！！")
                showView.value = false
                showViewTop.value = false
            }
    }


    fun getMoreMainData() {
        pageCount++
        loadMoreEnd.value = 0
        mainPageDataMore.postValue(null)
        retrofit.create(WanNetApi::class.java).getMainPageData(pageCount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mainPageDataMore.value = it
                    loadMoreEnd.value = 1
                }, {
                    pageCount--
                    loadMoreEnd.value = 2
                })
    }


    fun collect(id: String, call: CallBack) {
        retrofit.create(WanNetApi::class.java).collect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccess) {
                    call.onSuccess()
                } else {
                    call.onFail()
                }
            }, {
                Log.d(TAG, "collect: 错误 ${it.toString()}")
            })
    }

    fun unCollect(id: String) {
        retrofit.create(WanNetApi::class.java).unCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(getApplication(), "取消收藏成功", Toast.LENGTH_SHORT).show()
            }, {
                Log.d(TAG, "unCollect: 错误 ${it.toString()}")
            })
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    interface CallBack {
        fun onSuccess()

        fun onFail()
    }
}