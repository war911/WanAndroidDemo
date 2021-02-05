package com.example.mvvm.utils

import android.content.Context
import android.text.TextUtils

/**
 * Created by Warren on 2018/7/11.
 */
object PrefUtils {
    /**
     * SharedPreferences默认文件名称
     */
    private const val PREF_DEFAULT_FILE_NAME = "shared_prefs"

    /**
     * 加密接口，启用回调需要实现此接口
     */
    private var encryption: Encryption? = null

    /**
     * 初始化加密接口
     *
     * @param encryption
     */
    private fun initPrefs(encryption: Encryption?) {
        if (encryption != null) {
            PrefUtils.encryption = encryption
        }
    }

    /**
     * functionName: saveKeyValue
     *
     *
     * description:  保存key,value
     *
     *
     * params: context
     *
     *
     * params: key
     *
     *
     * params: value
     *
     *
     * params: synchronization 是否同步,同步提交返回值表示提交成功与否，异步提交返回值没意义
     *
     *
     * return: void
     *
     *
     * author: Warren
     *
     *
     * date: 2018/7/11
     *
     *
     */
    fun saveKeyValue(
        context: Context?,
        key: String?,
        value: String?,
        synchronization: Boolean
    ): Boolean {
        var value = value
        requireNotNull(context) { "Params context can't be null" }
        require(!TextUtils.isEmpty(key)) { "Params key can't be null or empty string" }
        val sharedPreferences =
            context.getSharedPreferences(PREF_DEFAULT_FILE_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            val edit = sharedPreferences.edit()
            if (encryption != null) {
                value = try {
                    encryption!!.encrypt(value)
                } catch (e: Exception) {
                    throw EncryptionException("加密错误")
                }
            }
            edit.putString(key, value)
            if (synchronization) {
                return edit.commit()
            } else {
                edit.apply()
            }
        }
        return false
    }

    /**
     * functionName: saveKeyValue
     *
     *
     * description:  重载方法
     *
     *
     * params: context
     *
     *
     * params: key
     *
     *
     * params: value
     *
     *
     * params: synchronization  同步提交返回值表示提交成功与否，异步提交返回值没意义
     *
     *
     * return: void
     *
     *
     * author: Warren
     *
     *
     * date: 2018/7/12
     *
     *
     */
    fun saveKeyValue(
        context: Context?,
        key: String?,
        value: Boolean,
        synchronization: Boolean
    ): Boolean {
        requireNotNull(context) { "Params context can't be null" }
        val sharedPreferences =
            context.getSharedPreferences(PREF_DEFAULT_FILE_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            val edit = sharedPreferences.edit()
            edit.putBoolean(key, value)
            if (synchronization) {
                return edit.commit()
            } else {
                edit.apply()
            }
        }
        return false
    }

    /**
     * functionName: saveKeyValue
     *
     *
     * description:  异步保存key和value
     *
     *
     * params: context
     *
     *
     * params: key
     *
     *
     * params: value
     *
     *
     * return: void
     *
     *
     * author: Warren
     *
     *
     * date: 2018/7/11
     *
     *
     */
    fun saveKeyValue(context: Context?, key: String?, value: Boolean) {
        saveKeyValue(context, key, value, true)
    }

    /**
     * functionName: saveKeyValue
     *
     *
     * description:  异步保存key和value
     *
     *
     * params: context
     *
     *
     * params: key
     *
     *
     * params: value
     *
     *
     * return: void
     *
     *
     * author: Warren
     *
     *
     * date: 2018/7/11
     *
     *
     */
    fun saveKeyValue(context: Context?, key: String?, value: String?) {
        saveKeyValue(context, key, value, true)
    }

    /**
     * functionName: getValue
     *
     *
     * description:  获取某个key对应的value
     *
     *
     * params: context
     *
     *
     * params: key
     *
     *
     * params: defaultValue
     *
     *
     * return: java.lang.String
     *
     *
     * author: Warren
     *
     *
     * date: 2018/7/11
     *
     *
     */
    fun getValue(context: Context?, key: String?, defaultValue: String?): String? {
        requireNotNull(context) { "Params context can't be null" }
        require(!TextUtils.isEmpty(key)) { "Params key can't be null or empty string" }
        var value: String? = null
        val sharedPreferences =
            context.getSharedPreferences(PREF_DEFAULT_FILE_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            value = sharedPreferences.getString(key, defaultValue)
            if (encryption != null) {
                value = try {
                    encryption!!.decrypt(value)
                } catch (e: Exception) {
                    throw EncryptionException("解密错误")
                }
            }
        }
        return value
    }

    /**
     * functionName: getValue
     *
     *
     * description:  获取boolean
     *
     *
     * params: context
     *
     *
     * params: key
     *
     *
     * params: defaultValue
     *
     *
     * return: boolean
     *
     *
     * author: Warren
     *
     *
     * date: 2018/7/16
     *
     *
     */
    fun getValue(context: Context?, key: String?, defaultValue: Boolean): Boolean {
        requireNotNull(context) { "Params context can't be null" }
        require(!TextUtils.isEmpty(key)) { "Params key can't be null or empty string" }
        var value = false
        val sharedPreferences =
            context.getSharedPreferences(PREF_DEFAULT_FILE_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            value = sharedPreferences.getBoolean(key, defaultValue)
        }
        return value
    }

    /**
     * functionName: clearPrefs
     *
     *
     * description:  清楚所有prefs
     *
     *
     * params: context
     *
     *
     * return: void
     *
     *
     * author: Warren
     *
     *
     * date: 2018/7/11
     *
     *
     */
    fun clearPrefs(context: Context?) {
        requireNotNull(context) { "Params context can't be null" }
        val sharedPreferences =
            context.getSharedPreferences(PREF_DEFAULT_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.clear()?.commit()
    }

    /**
     * functionName: removeKeyValue
     *
     *
     * description:  删除某个key对应的value
     *
     *
     * params: context
     *
     *
     * params: key
     *
     *
     * return: void
     *
     *
     * author: Warren
     *
     *
     * date: 2018/7/11
     *
     *
     */
    fun removeKeyValue(context: Context?, key: String?) {
        requireNotNull(context) { "Params context can't be null" }
        require(!TextUtils.isEmpty(key)) { "Params key can't be null or empty string" }
        val sharedPreferences =
            context.getSharedPreferences(PREF_DEFAULT_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.remove(key)?.commit()
    }

    /**
     * 加/解密接口
     */
    interface Encryption {
        /**
         * functionName: encrypt
         *
         *
         * description:  加密方法
         *
         *
         * params:  encrypt 加密字符串
         *
         *
         * return: java.lang.String
         *
         *
         * author: Warren
         *
         *
         * date: 2018/7/6
         *
         *
         */
        @Throws(Exception::class)
        fun encrypt(encrypt: String?): String?

        /**
         * functionName: decrypt
         *
         *
         * description:  解密方法
         *
         *
         * params: decrypt 解密字符串
         *
         *
         * return: java.lang.String
         *
         *
         * author: Warren
         *
         *
         * date: 2018/7/6
         *
         *
         */
        @Throws(Exception::class)
        fun decrypt(decrypt: String?): String?
    }

    /**
     * 自定义加解密异常类
     */
    private class EncryptionException : RuntimeException {
        constructor() {}
        constructor(message: String?) : super(message) {}
    }
}