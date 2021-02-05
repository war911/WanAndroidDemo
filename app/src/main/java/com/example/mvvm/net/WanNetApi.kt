package com.example.mvvm.net

import com.example.mvvm.data.*
import io.reactivex.Observable
import retrofit2.http.*

interface WanNetApi {

//    @GET("/banner/json")
//    fun getMainBanner(): Call<MainBanner>
//
//    @GET("/article/list/{page}/json")
//    fun getMainPageData(@Path("page") page: Int):Call<MainPageData>
//

    @GET("/banner/json")
    fun getMainBanner(): Observable<MainBanner>

    @GET("/article/list/{page}/json")
    fun getMainPageData(@Path("page") page: Int): Observable<MainPageData>

    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Observable<ApiResponse<SignUpUser>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<ApiResponse<Any>>


    @GET("user/logout/json")
    fun logOut(): Observable<ApiResponse<Any>>

    @GET("lg/collect/list/{page}/json")
    fun getFavorites(@Path("page") page: Int): Observable<FavoritesData>

    @GET("lg/coin/userinfo/json")
    fun getUserRank(): Observable<RankData>

    @POST("lg/collect/{id}/json")
    fun collect(@Path("id") id: String): Observable<ApiResponse<Any>>

    @POST("lg/uncollect_originId/{id}/json")
    fun unCollect(@Path("id") id: String): Observable<ApiResponse<Any>>

    @GET("wxarticle/chapters/json")
    suspend fun getWxList(): WxData

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWxContentList(
        @Path("id") id: String,
        @Path("page") page: Int
    ): Any
}
