package navigation

import kotlinx.serialization.Serializable

/**
 * @author wanglei
 * @date 2025/12/3 16:43
 * @Description：
 */

@Serializable
object HomeScreen

@Serializable
data class DetailScreen(val id: Long)

@Serializable
object TreePage
