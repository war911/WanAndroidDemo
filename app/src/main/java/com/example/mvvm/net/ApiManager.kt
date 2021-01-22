package com.example.mvvm.net

import android.content.Context
import android.os.Environment
import com.example.mvvm.App.Companion.mContext
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * @author YCKJ3655
 */
class ApiManager() {
    init {
        init()
    }
    companion object {
        private var apiManager: ApiManager = ApiManager()
        private lateinit var baseRetrofit: Retrofit
        fun getApiManager(): ApiManager? {
            return apiManager
        }

        private fun init() {

            val cacheDir = File(getProjectCachePath(mContext), "okhttp")
            val mCache = Cache(cacheDir, 8 * 1024 * 1024)

            val client = OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .cache(mCache)
                .build()

            baseRetrofit = Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(client)
                .build()
        }

        fun getProjectCachePath(context: Context): String? {
            return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                || !Environment.isExternalStorageRemovable()
            ) {
                context.externalCacheDir?.path
            } else {
                context.cacheDir.path
            }
        }
    }

    fun getBaseNet(): Retrofit {
        return baseRetrofit;
    }




}