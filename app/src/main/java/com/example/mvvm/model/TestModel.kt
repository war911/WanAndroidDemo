package com.example.mvvm.model

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class TestModel :ViewModel() {
    companion object{
        private const val TAG = "TestModel"
    }

    private var viewModelJob = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun launchDataLoad() {
        uiScope.launch {
            sortList()
            Log.d(Companion.TAG, "launchDataLoad: ok现在全部结束")
        }
    }

    suspend fun sortList() = withContext(Dispatchers.Default) {
        // Heavy work
        Log.d(Companion.TAG, "sortList: 模拟网络请求开始 ")
        delay(3000)
        Log.i(Companion.TAG, "sortList: 模拟网络请求 3秒结束 ")
    }

}