package com.example.mvvm.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.net.ApiManager
import com.example.mvvm.net.WanNetApi
import com.example.mvvm.utils.PrefUtils
import com.google.android.material.internal.ContextUtils.getActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

const val KEY_NAME = "name"
const val KEY_PASS = "pass"

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private var retrofit: Retrofit = ApiManager.getApiManager()!!.getBaseNet()
    private var _go = MutableLiveData<Boolean>(false)
    private var _name = MutableLiveData<String>("登录")
    val name = _name
    val go = _go

    @SuppressLint("CheckResult")
    fun signUp(userName: String?, userPassWord: String?, rePassWord: String?) {
        if (userName.isNullOrEmpty() || userPassWord.isNullOrEmpty() || rePassWord.isNullOrEmpty()) {
            Toast.makeText(getApplication(), "用户名或者密码不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (userPassWord != rePassWord) {
            Toast.makeText(getApplication(), "两次输入密码不相同", Toast.LENGTH_SHORT).show()
        }
        retrofit.create(WanNetApi::class.java)
            .register(userName, userPassWord, repassword = rePassWord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                it?.let {
                    Log.d(TAG, "signUp: 数据是 ${it.toString()}")
                    if (it.errorCode == -1) {
                        Toast.makeText(getApplication(), "异常${it.toString()}", Toast.LENGTH_SHORT)
                            .show()
                    } else {

                    }
                }
            }.observeOn(Schedulers.io())
            .flatMap { retrofit.create(WanNetApi::class.java).login(userName, userPassWord) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, "signUp:结果 ${it.toString()}")
                    Toast.makeText(getApplication(), "成功登陆", Toast.LENGTH_SHORT).show()
                    PrefUtils.saveKeyValue(getApplication(), KEY_NAME, userName)
                    PrefUtils.saveKeyValue(getApplication(), KEY_PASS, userPassWord)
                    _name.value = userName
                    _go.value = true
                },
                {
                    Log.d(TAG, "signUp: a错误 ${it.toString()}")
                })
    }




    companion object {
        private const val TAG = "SignupViewModel"
    }
}