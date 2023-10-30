package com.gm.ai.guidebook.ui.screen.settings.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.core.android.extensions.clickableWithoutRipple
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/30/2023
 */

@Composable
fun LogOutSection(
    modifier: Modifier = Modifier,
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
            modifier = Modifier
                .padding(start = GuideTheme.offset.regular)
                .clickableWithoutRipple(onClick),
            text = stringResource(R.string.log_out),
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.error,
        )
    }
}