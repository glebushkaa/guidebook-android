package com.gm.ai.guidebook.ui.screen.settings.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/30/2023
 */

@Composable
fun NotificationSection(
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {},
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
            text = stringResource(R.string.notifications),
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.onSurface,
        )
        Switch(
            modifier = Modifier
                .padding(end = GuideTheme.offset.regular)
                .align(Alignment.CenterEnd),
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = GuideTheme.palette.primary,
            )
        )
    }
}