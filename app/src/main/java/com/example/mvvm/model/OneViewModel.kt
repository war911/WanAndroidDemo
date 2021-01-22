package com.example.mvvm.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.data.MainBanner
import com.example.mvvm.net.ApiManager
import com.example.mvvm.net.WanNetApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class OneViewModel constructor() : ViewModel() {
    companion object{
        private const val TAG = "OneViewModel"
    }
    lateinit var retrofit: Retrofit
    var mutableLiveData: MutableLiveData<String> = MutableLiveData()



    init {
        retrofit = ApiManager.getApiManager()!!.getBaseNet()
    }

    fun getValue():String{
        return mutableLiveData.value.toString()
    }

    @SuppressLint("CheckResult")
    fun getMainBanner() {
//            retrofit.create(WanNetApi::class.java).getMainBanner().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    it.toString()
//                    mutableLiveData.value = it.toString()
//                    Log.d(TAG, "getMainBanner: ${mutableLiveData.value}")
//                }
    }

    override fun onCleared() {
        super.onCleared()
    }
}