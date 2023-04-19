package test.android.cns

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

internal class App : Application() {
    companion object {
        var items: List<String>? = null
    }

    object Theme {
        val shadow = Color.Black
//        val background = Color.Black
//        val foreground = Color.White
        val background = Color.White
        val foreground = Color.Black
        @Composable
        fun Composition(content: @Composable () -> Unit) {
            CompositionLocalProvider(
                content = content,
            )
        }
    }
}
