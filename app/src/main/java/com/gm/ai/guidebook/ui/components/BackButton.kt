package com.gm.ai.guidebook.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Preview
@Composable
fun BackButtonPreview() {
    GuideBookTheme(darkTheme = true) {
        BackButton(onClick = {})
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    tint: Color = GuideTheme.palette.onBackground,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.rotate(180f),
            painter = painterResource(id = R.drawable.ic_arrow_next),
            contentDescription = null,
            tint = tint,
        )
    }
}
