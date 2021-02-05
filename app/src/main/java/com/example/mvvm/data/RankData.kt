package com.example.mvvm.data

import androidx.lifecycle.LiveData

class RankData(
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String


){
    override fun toString(): String {
        return "RankData(`data`=$`data`, errorCode=$errorCode, errorMsg='$errorMsg')"
    }
}

data class Data(
    val coinCount: Int,
    val level: Int,
    val nickname: String,
    val rank: String,
    val userId: Int,
    val username: String


) {
    override fun toString(): String {
        return "Data(coinCount=$coinCount, level=$level, nickname='$nickname', rank='$rank', userId=$userId, username='$username')"
    }
}