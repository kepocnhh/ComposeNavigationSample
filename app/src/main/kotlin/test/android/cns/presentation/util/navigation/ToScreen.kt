package test.android.cns.presentation.util.navigation

import androidx.compose.animation.core.Animatable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import test.android.cns.App
import test.android.cns.presentation.util.androidx.compose.foundation.catchClicks
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun ToScreen(
    delay: Duration = 2.seconds,
    content: @Composable () -> Unit,
) {
    val initialWidth = LocalConfiguration.current.screenWidthDp.dp
    val targetWidth = initialWidth // todo orientation
    var actual by rememberSaveable { mutableStateOf(1f) }
    val animatable = remember { Animatable(initialValue = actual) }
    val target = 0f
    val show = true // todo direction
    LaunchedEffect(Unit) {
        val duration = delay * (actual - target).toDouble()
        animatable.animateTo(
            targetValue = target,
            animationSpec = tween(
                durationMillis = duration.inWholeMilliseconds.toInt(),
                easing = LinearEasing
            ),
        )
    }
    Box(Modifier.fillMaxSize()) {
        actual = animatable.value
        val alpha = actual - target
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(App.Theme.shadow.copy(alpha = alpha)),
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(targetWidth)
                .offset(x = initialWidth * animatable.value + (initialWidth - targetWidth))
                .catchClicks(),
        ) {
            content()
        }
    }
}
