package com.example.mvvm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.chad.library.adapter.base.module.LoadMoreModuleConfig

class App : Application() {
    companion object{
        lateinit var mContext:App
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        LoadMoreModuleConfig.defLoadMoreView
    }
}