package test.android.cns

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import test.android.cns.presentation.module.router.RouterScreen

internal class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App.Theme.Composition {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(App.Theme.background),
                ) {
                    BackHandler {
                        finish()
                    }
                    RouterScreen()
                }
            }
        }
    }
}
