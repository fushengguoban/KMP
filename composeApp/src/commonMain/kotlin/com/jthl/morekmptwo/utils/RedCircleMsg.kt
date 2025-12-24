package com.jthl.morekmptwo.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jthl.morekmptwo.resources.Res
import com.jthl.morekmptwo.resources.compose_multiplatform
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * @author wanglei
 * @date 2025/12/15 14:37
 * @Description：
 */

@Composable
fun IconWithMsg(resId: DrawableResource, msgCount: Int) {
    Box(modifier = Modifier.size(80.dp)) {
        Image(
            painter = painterResource(resource =resId),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(50.dp).align(Alignment.Center)
        )
        //根据不同的状态，来组合不同的组件
        if (msgCount > 0) {
            Box(
                modifier = Modifier.clip(CircleShape).background(Color.Red).size(25.dp)
                    .align(alignment = Alignment.TopEnd)
            ) {
                Text(
                    text = "$msgCount",
                    color = Color.White,
                    modifier = Modifier.wrapContentSize().align(alignment = Alignment.Center)
                )
            }
        }

    }
}