package test.android.cns.presentation.module.router

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import test.android.cns.App
import test.android.cns.presentation.module.noitems.NoItemsScreen
import test.android.cns.presentation.util.navigation.ToScreen
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun RouterScreen() {
    Box(Modifier.fillMaxSize()) {
        BasicText(
            modifier = Modifier.align(Alignment.Center),
            text = "router screen",
            style = TextStyle(color = App.Theme.foreground),
        )
        val items = App.items
        if (items == null) {
            ToScreen(
                delay = 2.seconds,
                target = 0.25f,
            ) {
                NoItemsScreen()
            }
        } else {
            TODO()
        }
    }
}
