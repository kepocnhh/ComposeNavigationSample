package test.android.cns.presentation.module.noitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import test.android.cns.App

@Composable
internal fun NoItemsScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(App.Theme.background)
    ) {
        BasicText(
            modifier = Modifier.align(Alignment.Center),
            text = "no items",
            style = TextStyle(color = App.Theme.foreground),
        )
    }
}
