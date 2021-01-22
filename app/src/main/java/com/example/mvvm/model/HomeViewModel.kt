package com.example.mvvm.model

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.data.MainBanner
import com.example.mvvm.data.MainPageData
import com.example.mvvm.net.ApiManager
import com.example.mvvm.net.WanNetApi
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.delay as delay1

class HomeViewModel : ViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private var viewModelJob = SupervisorJob()
    private var pageCount = 0

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var retrofit: Retrofit = ApiManager.getApiManager()!!.getBaseNet()
    val mainBannerData: MutableLiveData<MainBanner> = MutableLiveData()
    val mainPageData: MutableLiveData<MainPageData> = MutableLiveData()
    val mainPageDataMore: MutableLiveData<MainPageData> = MutableLiveData()
    val loadMoreEnd: MutableLiveData<Int> = MutableLiveData(0)
    var showView: MutableLiveData<Boolean> = MutableLiveData(false)


    @SuppressLint("CheckResult")
    suspend fun getMainData() {
        showView.postValue(false)
        val time = measureTimeMillis {
            withContext(Dispatchers.IO) {
                var mainBanner = async {
                    getMainBanner()
                }
                var mainPager = async {
                    getMainPager()
                }
                Log.d(TAG, "getMainData: ${mainBanner.await() && mainPager.await()} ")
            }
        }
        showView.postValue(true)
        Log.d(TAG, "getMainData: 执行花费的时间 $time")
    }


    fun getMoreMainData(){
        pageCount++
        loadMoreEnd.value = 0
        mainPageDataMore.postValue(null)
        retrofit.create(WanNetApi::class.java).getMainPageData(pageCount).enqueue(object :Callback<MainPageData>{
            override fun onFailure(call: Call<MainPageData>, t: Throwable) {
                pageCount--
                loadMoreEnd.value = 2
            }

            override fun onResponse(call: Call<MainPageData>, response: Response<MainPageData>) {
                mainPageDataMore.value = response.body()
                loadMoreEnd.value = 1
            }

        })
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

    }


    private suspend fun getMainPager(): Boolean = Dispatchers.IO {
        mainPageData.postValue(null)
        retrofit.create(WanNetApi::class.java).getMainPageData(0)
            .enqueue(object : Callback<MainPageData> {
                override fun onFailure(call: Call<MainPageData>, t: Throwable) {
                    Log.i(TAG, "onFailure: $t")
                }

                override fun onResponse(
                    call: Call<MainPageData>,
                    response: Response<MainPageData>
                ) {
                    Log.d(TAG, "onResponse: 响应，有结果")
                    mainPageData.value = response.body()
                }

            })
        Log.d(TAG, "getMainPager: 代码跑完1")
        return@IO true
    }

    private suspend fun getMainBanner(): Boolean = Dispatchers.IO {
        mainBannerData.postValue(null)
        retrofit.create(WanNetApi::class.java).getMainBanner()
            .enqueue(object : Callback<MainBanner> {
                override fun onFailure(call: Call<MainBanner>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<MainBanner>, response: Response<MainBanner>) {
                    mainBannerData.value = response.body()
                    response.body()?.getData()?.forEach { it ->
                        Log.d(TAG, "getMainBanner: ${it.title} ")
                    }
                }
            })
        Log.d(TAG, "getMainBanner: 代码跑完2")
        return@IO true
    }

}