package com.example.mvvm.data

import androidx.lifecycle.LiveData

class  MainBanner : LiveData<MainBanner>() {
     /**
      * data : [{"desc":"享学~","id":29,"imagePath":"https://www.wanandroid.com/blogimgs/8e95ad05-a6f5-4c65-8a89-f8d4b819aa80.jpeg","isVisible":1,"order":0,"title":"做了5年Android，靠这份面试题和答案从12K涨到30K","type":0,"url":"https://mp.weixin.qq.com/s/oxoocfuPBS-fYI1Y0HU5QQ"},{"desc":"","id":6,"imagePath":"https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png","isVisible":1,"order":1,"title":"我们新增了一个常用导航Tab~","type":1,"url":"https://www.wanandroid.com/navi"},{"desc":"一起来做个App吧","id":10,"imagePath":"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png","isVisible":1,"order":1,"title":"一起来做个App吧","type":1,"url":"https://www.wanandroid.com/blog/show/2"},{"desc":"","id":20,"imagePath":"https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png","isVisible":1,"order":2,"title":"flutter 中文社区 ","type":1,"url":"https://flutter.cn/"}]
      * errorCode : 0
      * errorMsg :
      */
     private var errorCode = 0
     private var errorMsg: String? = null
     private var data: List<DataBean>? = null

     fun getErrorCode(): Int {
         return errorCode
     }

     fun setErrorCode(errorCode: Int) {
         this.errorCode = errorCode
     }

     fun getErrorMsg(): String? {
         return errorMsg
     }

     fun setErrorMsg(errorMsg: String?) {
         this.errorMsg = errorMsg
     }

     fun getData(): List<DataBean>? {
         return data
     }

     fun setData(data: List<DataBean>?) {
         this.data = data
     }

     override fun toString(): String {
         return "MainBanner(errorCode=$errorCode, errorMsg=$errorMsg, data=$data)"
     }

     class DataBean {

         /**
          * desc : 享学~
          * id : 29
          * imagePath : https://www.wanandroid.com/blogimgs/8e95ad05-a6f5-4c65-8a89-f8d4b819aa80.jpeg
          * isVisible : 1
          * order : 0
          * title : 做了5年Android，靠这份面试题和答案从12K涨到30K
          * type : 0
          * url : https://mp.weixin.qq.com/s/oxoocfuPBS-fYI1Y0HU5QQ
          */
         var desc: String? = null
         var id = 0
         var imagePath: String? = null
         var isVisible = 0
         var order = 0
         var title: String? = null
         var type = 0
         var url: String? = null

         override fun toString(): String {
             return "DataBean(desc=$desc, id=$id, imagePath=$imagePath, isVisible=$isVisible, order=$order, title=$title, type=$type, url=$url)"
         }

     }

 }