package com.gm.ai.guidebook.ui.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.core.common.FIVE_HUNDRED_MILLIS
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Composable
fun SplashScreen() {
    var logoAlpha by remember { mutableFloatStateOf(LOGO_START_ALPHA) }
    val animatedLogoAlpha by animateFloatAsState(
        animationSpec = tween(FIVE_HUNDRED_MILLIS.toInt()),
        targetValue = logoAlpha,
        label = "",
    )

    Box(
        modifier = Modifier
            .background(GuideTheme.palette.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.graphicsLayer {
                alpha = animatedLogoAlpha
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = GuideTheme.offset.medium,
            ),
        ) {
            Image(
                modifier = Modifier.size(
                    dimensionResource(R.dimen.splash_logo_image_size),
                ),
                painter = painterResource(id = R.drawable.img_book),
                contentDescription = null,
            )
            Text(
                text = stringResource(R.string.app_name),
                style = GuideTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                ),
                color = GuideTheme.palette.onBackground,
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        logoAlpha = LOGO_END_ALPHA
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    GuideBookTheme(darkTheme = true) {
        SplashScreen()
    }
}

private const val LOGO_START_ALPHA = 0f
private const val LOGO_END_ALPHA = 1f
