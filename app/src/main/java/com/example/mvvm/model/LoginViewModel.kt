package com.example.mvvm.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.net.ApiManager
import com.example.mvvm.net.WanNetApi
import com.example.mvvm.utils.PrefUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val mRetrofit = ApiManager.getApiManager()!!.getBaseNet()
    private val _name = MutableLiveData<String>("登录")
    val name = _name

    fun login(name: String?, pass: String?) {
        if(name.isNullOrEmpty() || pass.isNullOrEmpty()){
            Toast.makeText(getApplication(), "用户名或者密码不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        mRetrofit.create(WanNetApi::class.java).login(name,pass).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "login: name $name  pass $pass")
                _name.value = name
                PrefUtils.saveKeyValue(getApplication(), KEY_NAME,name)
                PrefUtils.saveKeyValue(getApplication(), KEY_PASS,pass)
            }, {
                Toast.makeText(getApplication(), "错误 $it", Toast.LENGTH_SHORT).show()
            })

    }

    companion object {
        private const val TAG = "LoginVIewModel"
    }
}