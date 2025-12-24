package com.jthl.morekmptwo.utils

import com.jthl.morekmptwo.data.MoreOriginList
import com.jthl.morekmptwo.resources.Res
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

/**
 * @author wanglei
 * @date 2025/12/17 14:41
 * @Description：
 */

suspend fun loadTreeConfig(): MoreOriginList{
    val bytes = Res.readBytes("files/Tree.json")
    val jsonString = bytes.decodeToString()
    return json.decodeFromString(jsonString)
}