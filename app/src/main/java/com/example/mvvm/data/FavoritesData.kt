package com.example.mvvm.data

import androidx.lifecycle.LiveData

class FavoritesData : LiveData<FavoritesData>() {
    /**
     * data : {"curPage":1,"datas":[{"author":"","chapterId":502,"chapterName":"自助","courseId":13,"desc":"","envelopePic":"","id":172247,"link":"https://juejin.cn/post/6922020192662863886","niceDate":"19小时前","origin":"","originId":17080,"publishTime":1611822714000,"title":"Android事件分发机制四：学了事件分发有什么用？","userId":85902,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":1}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * curPage : 1
         * datas : [{"author":"","chapterId":502,"chapterName":"自助","courseId":13,"desc":"","envelopePic":"","id":172247,"link":"https://juejin.cn/post/6922020192662863886","niceDate":"19小时前","origin":"","originId":17080,"publishTime":1611822714000,"title":"Android事件分发机制四：学了事件分发有什么用？","userId":85902,"visible":0,"zan":0}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 1
         */
        var curPage = 0
        var offset = 0
        var over = false
        var pageCount = 0
        var size = 0
        var total = 0
        var datas: List<DatasBean>? = null
        
        class DatasBean {
            /**
             * author :
             * chapterId : 502
             * chapterName : 自助
             * courseId : 13
             * desc :
             * envelopePic :
             * id : 172247
             * link : https://juejin.cn/post/6922020192662863886
             * niceDate : 19小时前
             * origin :
             * originId : 17080
             * publishTime : 1611822714000
             * title : Android事件分发机制四：学了事件分发有什么用？
             * userId : 85902
             * visible : 0
             * zan : 0
             */
            var author: String? = null
            var chapterId = 0
            var chapterName: String? = null
            var courseId = 0
            var desc: String? = null
            var envelopePic: String? = null
            var id = 0
            var link: String? = null
            var niceDate: String? = null
            var origin: String? = null
            var originId = 0
            var publishTime: Long = 0
            var title: String? = null
            var userId = 0
            var visible = 0
            var zan = 0
            override fun toString(): String {
                return "DatasBean(author=$author, chapterId=$chapterId, chapterName=$chapterName, courseId=$courseId, desc=$desc, envelopePic=$envelopePic, id=$id, link=$link, niceDate=$niceDate, origin=$origin, originId=$originId, publishTime=$publishTime, title=$title, userId=$userId, visible=$visible, zan=$zan)"
            }


        }

        override fun toString(): String {
            return "DataBean(curPage=$curPage, offset=$offset, over=$over, pageCount=$pageCount, size=$size, total=$total, datas=$datas)"
        }
    }

    override fun toString(): String {
        return "FavoritesData(data=$data, errorCode=$errorCode, errorMsg=$errorMsg)"
    }
}