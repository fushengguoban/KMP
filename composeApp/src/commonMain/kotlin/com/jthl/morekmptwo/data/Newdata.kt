package com.jthl.morekmptwo.data

import kotlinx.serialization.Serializable

/**
 * @author wanglei
 * @date 2025/12/8 13:00
 * @Description：
 */

@Serializable
data class Newdata(
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String
){
    @Serializable
    data class Data(
        val curPage: Int,
        val datas: List<DataX>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
    )

    @Serializable
    data class DataX(
        val adminAdd: Boolean,
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val host: String,
        val id: Int,
        val isAdminAdd: Boolean,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<String>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
    )
}


