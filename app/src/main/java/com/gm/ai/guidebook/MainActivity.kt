package com.gm.ai.guidebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gm.ai.guidebook.ui.navigation.GuideNavHost
import com.gm.ai.guidebook.ui.theme.GuideBookTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuideApp()
        }
    }

    @Composable
    private fun GuideApp() {
        GuideBookTheme {
            val controller = rememberNavController()

            GuideNavHost(
                modifier = Modifier.fillMaxSize(),
                controller = controller,
            )
        }
    }
}
