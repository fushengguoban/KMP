package com.jthl.morekmptwo.data

import kotlinx.serialization.Serializable

/**
 * @author wanglei
 * @date 2025/12/11 11:15
 * @Description：
 */

@Serializable
data class data(
    val errorCode: Int,
    val errorMsg: String,
    val data: InfoData
)

@Serializable
data class InfoData(
    val snippets: String
)