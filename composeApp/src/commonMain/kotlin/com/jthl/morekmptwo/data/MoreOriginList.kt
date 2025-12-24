package com.jthl.morekmptwo.data

import kotlinx.serialization.Serializable

/**
 * @author wanglei
 * @date 2025/12/16 15:27
 * @Description：
 */
@Serializable
data class MoreOriginList(
    val `data`: MoreOriginData,
    val retcode: String,
    val retmsg: String,
    val success: Boolean,
    val total: String?
)

@Serializable
data class MoreOriginData(
    val children: List<Children>,
    val operateUserOwned: Boolean,
    val orgLevel: Int,
    val orgName: String,
    val orgNum: String,
    val owned: Boolean,
    val siteId: String?,
    val siteName: String?
)
@Serializable
data class Children(
    val children: List<ChildrenX>,
    val operateUserOwned: Boolean,
    val orgLevel: Int,
    val orgName: String,
    val orgNum: String,
    val owned: Boolean,
    val siteId: String?,
    val siteName: String?
)
@Serializable
data class ChildrenX(
    val children: List<ChildrenXX>,
    val operateUserOwned: Boolean,
    val orgLevel: Int,
    val orgName: String,
    val orgNum: String,
    val owned: Boolean,
    val siteId: String?,
    val siteName: String?
)
@Serializable
data class ChildrenXX(
    val children: List<String>,
    val operateUserOwned: Boolean,
    val orgLevel: Int,
    val orgName: String,
    val orgNum: String,
    val owned: Boolean,
    val siteId: String?,
    val siteName: String?
)