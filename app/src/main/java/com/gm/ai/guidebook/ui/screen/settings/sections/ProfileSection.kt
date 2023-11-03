package com.gm.ai.guidebook.ui.screen.settings.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.model.User
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/30/2023
 */

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    user: User,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(
                dimensionResource(id = R.dimen.settings_section_height)
            )
            .background(
                color = GuideTheme.palette.surface,
                shape = GuideTheme.shape.medium,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(start = GuideTheme.offset.medium)
                .size(
                    dimensionResource(id = R.dimen.profile_icon_size)
                )
                .background(
                    color = GuideTheme.palette.onBackground,
                    shape = GuideTheme.shape.round,
                ),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = null,
            tint = GuideTheme.palette.background,
        )
        Column(
            modifier = Modifier.padding(
                horizontal = GuideTheme.offset.regular,
            ),
        ) {
            Text(
                text = user.username,
                style = GuideTheme.typography.titleMedium,
                color = GuideTheme.palette.onSurface,
            )
            Text(
                text = user.email,
                style = GuideTheme.typography.bodyMedium,
                color = GuideTheme.palette.onSurface.copy(
                    alpha = EMAIL_TEXT_ALPHA,
                ),
            )
        }
    }
}

private const val EMAIL_TEXT_ALPHA = 0.6f