package test.android.cns.presentation.module.noitems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.android.cns.App

@Composable
internal fun NoItemsScreen(
    onCreate: () -> Unit,
) {
    val logger = App.newLogger("[NoItems]")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(App.Theme.background)
    ) {
        logger.debug("recompose...")
        Column(Modifier.fillMaxWidth().align(Alignment.Center)) {
            BasicText(
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp)
                    .wrapContentHeight(),
                text = "no items",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = App.Theme.foreground,
                ),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp)
                    .clickable {
                        onCreate()
                    }
                    .wrapContentHeight(),
                text = "+",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = App.Theme.primary,
                ),
            )
        }
    }
}
