package test.android.cns

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

internal class App : Application() {
    object Theme {
        @Composable
        fun Composition(content: @Composable () -> Unit) {
            CompositionLocalProvider(
                content = content,
            )
        }
    }
}
