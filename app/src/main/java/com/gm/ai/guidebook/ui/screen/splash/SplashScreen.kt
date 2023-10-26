package com.gm.ai.guidebook.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme
import kotlinx.coroutines.delay

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Composable
fun SplashScreen(
    homeNavigate: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideTheme.palette.background),
        verticalArrangement = Arrangement.spacedBy(
            space = GuideTheme.offset.average,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(160.dp),
            painter = painterResource(id = R.drawable.img_book),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(bottom = GuideTheme.offset.average),
            text = "GuideBook",
            style = GuideTheme.typography.headlineMedium,
            color = GuideTheme.palette.onBackground,
        )
    }

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        homeNavigate()
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    GuideBookTheme(darkTheme = true) {
        SplashScreen()
    }
}
