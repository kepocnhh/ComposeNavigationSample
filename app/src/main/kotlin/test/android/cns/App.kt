package test.android.cns

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import test.android.cns.foundation.provider.logger.Logger
import test.android.cns.foundation.provider.logger.LoggerFactory
import test.android.cns.implementation.provider.logger.FinalLoggerFactory

internal class App : Application() {
    companion object {
        private val _loggerFactory: LoggerFactory = FinalLoggerFactory

        @Composable
        fun newLogger(tag: String): Logger {
            return remember(tag) {
                _loggerFactory.newLogger(tag)
            }
        }

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
