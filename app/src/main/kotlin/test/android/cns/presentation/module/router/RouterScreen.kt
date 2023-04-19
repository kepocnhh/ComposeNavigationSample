package test.android.cns.presentation.module.router

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import test.android.cns.App
import test.android.cns.presentation.module.items.Items
import test.android.cns.presentation.module.noitems.NoItemsScreen
import test.android.cns.presentation.util.navigation.ScreenHolder
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun RouterScreen() {
    val logger = App.newLogger("[Router]")
    ScreenHolder {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(App.Theme.background),
        ) {
            BasicText(
                modifier = Modifier.align(Alignment.Center),
                text = "router screen",
                style = TextStyle(color = App.Theme.foreground),
            )
            val items = remember { App.items }.value
            if (items == null) {
                logger.debug("no items")
                ToScreen(
                    tag = "NoItems",
                    delay = 1.seconds
                ) {
                    logger.debug("to screen no items...")
                    NoItemsScreen(
                        onCreate = {
                            logger.debug("on create...")
                            back {
                                logger.debug("on back...")
                                App.items.value = listOf()
                            }
                        }
                    )
                }
            } else {
                logger.debug("items: $items")
                ToScreen(
                    tag = "Items",
                    delay = 1.seconds
                ) {
                    logger.debug("to screen items...")
                    Items()
                }
            }
        }
    }
}
