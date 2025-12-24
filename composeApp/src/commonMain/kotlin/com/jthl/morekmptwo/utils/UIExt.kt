package com.jthl.morekmptwo.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


/**
 * @author wanglei
 * @date 2025/12/12 13:11
 * @Description：
 */

@OptIn(ExperimentalTime::class)
@Composable
fun PulseIndicator(
    icon: DrawableResource,
    modifier: Modifier
) {
    val periodMs = 3600L
    val offsetsMs = longArrayOf(0L, 1200L, 2400L)
    val startNs = remember { Clock.System.now().toEpochMilliseconds() }
//    var frameTimeNs: Long by remember { mutableStateOf(startNs) }
//    LaunchedEffect(Unit) {
//        while (true) {
//            withFrameNanos { now -> frameTimeNs = now }
//        }
//    }

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(periodMs.toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    fun phase(offsetMs: Long): Float {
        val offset = (offsetMs.toFloat() / periodMs.toFloat())
        return ((progress + offset) % 1f)
    }


    Box(modifier.size(80.dp), contentAlignment = Alignment.Center) {
        @Composable
        fun Ring(p: Float) = Box(
            Modifier.matchParentSize()
                .graphicsLayer {
                    scaleX = 1f + 0.8f * p
                    scaleY = 1f + 0.8f * p
                    alpha = 1f - p
                }.border(1.5.dp, Color.Red.copy(alpha = 0.9f), CircleShape)
        )

        Ring(phase(offsetsMs[0]))
        Ring(phase(offsetsMs[1]))
        Ring(phase(offsetsMs[2]))

        Box(
            modifier.size(80.dp).background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = modifier.size(32.dp)
            )
        }
    }

}