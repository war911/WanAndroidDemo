package com.example.mvvm.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.R
import com.example.mvvm.data.ApiResponse
import com.example.mvvm.data.RankData
import com.example.mvvm.net.ApiManager
import com.example.mvvm.net.WanNetApi
import com.example.mvvm.utils.PrefUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var retrofit: Retrofit = ApiManager.getApiManager()!!.getBaseNet()
    private val _name = MutableLiveData<String>()
    var name = _name
    var showView: MutableLiveData<Boolean> = MutableLiveData(false)
    private var disposable: Disposable? = null
    private val _userRank = MutableLiveData<RankData?>()
    val userRank = _userRank
    var observableUserRank = retrofit.create(WanNetApi::class.java).getUserRank()
    private lateinit var observableLogin: Observable<ApiResponse<Any>>


    /**
     * Rxjava 嵌套网络请求 flatMap 完成1 有结果后 完成2
     */
    fun mainLogin(name: String?, pass: String?) {
        if (name.isNullOrEmpty() || pass.isNullOrEmpty()) {
            Toast.makeText(getApplication(), "账号或者密码不能为空", Toast.LENGTH_SHORT).show()
            return
        }

        disposable?.dispose()
        observableLogin = retrofit.create(WanNetApi::class.java).login(name, pass)

        observableLogin.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it.errorCode == 0) {
                    _name.value = name
                    PrefUtils.saveKeyValue(getApplication(),KEY_NAME,name)
                    PrefUtils.saveKeyValue(getApplication(), KEY_PASS,pass)

                }
            }.observeOn(Schedulers.io())
            .flatMap {
                observableUserRank
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.errorCode == 0) {
                    Log.d(
                        TAG, "mainLogin: 数据" +
                                " ${it.toString()}"
                    )
                    _userRank.value = it
                    Log.d(TAG, "mainLogin: 数据?? ${userRank.value} ")
                } else {
                    Log.d(TAG, "getUserRank: 数据异常${it.toString()} ")
                }
            }, {
                Log.d(TAG, "mainLogin: 错误$it")
            })
    }

    fun logOut() {
        disposable?.dispose()
        disposable = retrofit.create(WanNetApi::class.java).logOut()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isSuccess) {
                    _name.value = getApplication<Application>().getString(R.string.login)
                    PrefUtils.clearPrefs(getApplication())
                    _userRank.value = null
                }
            }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
