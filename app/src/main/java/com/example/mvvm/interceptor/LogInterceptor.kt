package com.example.mvvm.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 * date: 2019\6\11 0011
 * author: zlx
 * email: 1170762202@qq.com
 * description: log 拦截
 */
class LogInterceptor : Interceptor {
    private val TAG = "LogInterceptor"
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.w(TAG, "request:$request")
        printParams(request.body())
        val t1 = System.nanoTime()
        val response = chain.proceed(chain.request())
        val t2 = System.nanoTime()
        Log.i(
            TAG, String.format(
                Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()
            )
        )
        val mediaType = response.body()!!.contentType()
        val content = response.body()!!.string()
        Log.d(TAG, "response body: $content")
        return response.newBuilder()
            .body(ResponseBody.create(mediaType, content))
            .build()
    }

    private fun printParams(body: RequestBody?) {
        if (body == null) {
            return
        }
        val buffer = Buffer()
        try {
            body.writeTo(buffer)
            var charset = Charset.forName("UTF-8")
            val contentType = body.contentType()
            if (contentType != null) {
                charset = contentType.charset(charset)
            }
            val params = buffer.readString(charset)
            Log.e(TAG, "请求参数： | $params")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}