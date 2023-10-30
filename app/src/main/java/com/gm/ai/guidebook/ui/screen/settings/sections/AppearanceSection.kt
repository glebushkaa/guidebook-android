package com.gm.ai.guidebook.ui.screen.settings.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/30/2023
 */

@Composable
fun AppearanceSection(
    modifier: Modifier = Modifier,
    darkModeEnabled: Boolean,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                dimensionResource(id = R.dimen.settings_section_height)
            )
            .background(
                color = GuideTheme.palette.surface,
                shape = GuideTheme.shape.medium,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            modifier = Modifier.padding(start = GuideTheme.offset.regular),
            text = stringResource(R.string.appearance),
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.onSurface,
        )
        Button(
            modifier = Modifier
                .padding(end = GuideTheme.offset.regular)
                .size(
                    dimensionResource(id = R.dimen.appearance_button_size)
                )
                .align(Alignment.CenterEnd),
            contentPadding = PaddingValues(GuideTheme.offset.tiny),
            shape = GuideTheme.shape.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = GuideTheme.palette.onSurface.copy(
                    alpha = BUTTON_CONTAINER_COLOR_ALPHA
                ),
            ),
            onClick = onClick,
        ) {
            val icon = if (darkModeEnabled) R.drawable.ic_moon else R.drawable.ic_sun
            Icon(
                modifier = Modifier.size(
                    dimensionResource(id = R.dimen.appearance_icon_size)
                ),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = GuideTheme.palette.surface,
            )
        }
    }
}

private const val BUTTON_CONTAINER_COLOR_ALPHA = 0.4f