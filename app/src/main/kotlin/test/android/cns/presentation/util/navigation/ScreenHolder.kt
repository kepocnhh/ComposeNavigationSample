package test.android.cns.presentation.util.navigation

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import test.android.cns.App
import test.android.cns.presentation.util.androidx.compose.foundation.catchClicks
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal class ScreenHolderOld {
    class NextScreen(
        val delay: Duration = 250.milliseconds,
        val target: Float = 0f,
        val content: @Composable () -> Unit,
    )

    val next = mutableStateOf<NextScreen?>(null)

    @Composable
    fun ToScreen(
        delay: Duration = 250.milliseconds,
        target: Float = 0f,
        content: @Composable () -> Unit,
    ) {
        next.value = NextScreen(
            delay = delay,
            target = target,
            content = content,
        )
    }

    companion object {
        private suspend fun Animatable<Float, AnimationVector1D>.animate(
            actual: Float,
            target: Float,
            delay: Duration,
            easing: Easing = LinearEasing
        ) {
            if (actual - target <= 0) return
            val duration = delay * (actual - target).toDouble()
            animateTo(
                targetValue = target,
                animationSpec = tween(
                    durationMillis = duration.inWholeMilliseconds.toInt(),
                    easing = easing
                ),
            )
        }

        @Composable
        private fun Screen(
            width: Dp,
            alpha: Float,
            offset: Dp,
            content: @Composable () -> Unit,
        ) {
            Box(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(App.Theme.shadow.copy(alpha = alpha)),
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(width)
                        .offset(x = offset)
                        .catchClicks(),
                ) {
                    content()
                }
            }
        }

        @Composable
        fun ToScreen(
            delay: Duration = 250.milliseconds,
            target: Float = 0f,
            actual: MutableState<Float>,
            content: @Composable () -> Unit,
        ) {
            require(target in 0f..1f)
            require(delay.isPositive())
            val logger = App.newLogger("[SH|TS]")
            val initialWidth = LocalConfiguration.current.screenWidthDp.dp
            val targetWidth = initialWidth * (1f - target)
            val animatable = remember { Animatable(initialValue = actual.value) }
            val show = true // todo direction
            LaunchedEffect(Unit) {
                logger.debug("animate $actual to $target $delay")
                animatable.animate(
                    actual = actual.value,
                    target = target,
                    delay = delay,
                )
            }
            actual.value = animatable.value
            Screen(
                width = targetWidth,
                alpha = 1f - actual.value,
                offset = initialWidth * actual.value,
                content = content,
            )
        }
    }
}

@Composable
internal fun ScreenHolderOld(tag: String, content: @Composable ScreenHolderOld.() -> Unit) {
    val initialWidth = LocalConfiguration.current.screenWidthDp.dp
    val holder = remember { ScreenHolderOld() }
    val next = holder.next.value
    val actual = rememberSaveable { mutableStateOf(1f) }
    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = initialWidth * actual.value - initialWidth),
        ) {
            content(holder)
        }
        if (next != null) {
            ScreenHolderOld.ToScreen(
                delay = next.delay,
                actual = actual,
                target = next.target,
                content = next.content,
            )
        }
    }
}

private class NextScreen(
    val delay: Duration,
    val target: Float,
    val content: @Composable () -> Unit,
)

internal interface ScreenHolder {
    fun toScreen(
        delay: Duration = 250.milliseconds,
        target: Float = 0f,
        content: @Composable () -> Unit,
    )
}

private suspend fun Animatable<Float, AnimationVector1D>.animate(
    actual: Float,
    target: Float,
    delay: Duration,
    easing: Easing = LinearEasing
) {
    if (actual - target <= 0) return
    val duration = delay * (actual - target).toDouble()
    animateTo(
        targetValue = target,
        animationSpec = tween(
            durationMillis = duration.inWholeMilliseconds.toInt(),
            easing = easing
        ),
    )
}

private typealias Next = @Composable () -> Unit

@Composable
internal fun ScreenHolder(content: @Composable ScreenHolder.() -> Unit) {
    val logger = App.newLogger("[SH|TS]")
    val actualState = rememberSaveable { mutableStateOf(1f) }
    val targetState = rememberSaveable { mutableStateOf(1f) }
    val delayState = rememberSaveable { mutableStateOf(250.milliseconds.inWholeMilliseconds) }
    val initialWidth = LocalConfiguration.current.screenWidthDp.dp
    val targetWidth = initialWidth * (1f - targetState.value)
    val animatable = remember { Animatable(initialValue = actualState.value) }
    val show = true // todo direction
    val next = remember { mutableStateOf<Next?>(null) }
    val holder = remember {
        object : ScreenHolder {
            override fun toScreen(
                delay: Duration,
                target: Float,
                content: @Composable () -> Unit,
            ) {
                delayState.value = delay.inWholeMilliseconds
                targetState.value = target
                next.value = content
            }
        }
    }
    LaunchedEffect(Unit) {
        logger.debug("animate ${actualState.value} to ${targetState.value} ${delayState.value.milliseconds}")
        animatable.animate(
            actual = actualState.value,
            target = targetState.value,
            delay = delayState.value.milliseconds,
        )
    }
    actualState.value = animatable.value
    val alpha = 1f - actualState.value
    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = initialWidth * actualState.value - initialWidth),
        ) {
            holder.content()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(App.Theme.shadow.copy(alpha = alpha)),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(targetWidth)
                .offset(x = initialWidth * actualState.value)
                .catchClicks(),
        ) {
            next.value?.invoke()
        }
    }
}
