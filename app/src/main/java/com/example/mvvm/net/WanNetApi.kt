package com.example.mvvm.net

import com.example.mvvm.data.MainBanner
import com.example.mvvm.data.MainPageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WanNetApi {

    @GET("/banner/json")
    fun getMainBanner(): Call<MainBanner>

    @GET("/article/list/{page}/json")
    fun getMainPageData(@Path("page") page:Int):Call<MainPageData>


}