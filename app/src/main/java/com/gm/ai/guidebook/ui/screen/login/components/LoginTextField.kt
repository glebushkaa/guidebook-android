package com.gm.ai.guidebook.ui.screen.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit = {},
    placeholder: String = "",
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp),
        value = value,
        onValueChange = {
            val query = if (it.length > MAX_QUERY_LENGTH) {
                it.substring(0, MAX_QUERY_LENGTH)
            } else {
                it
            }
            onValueChanged(query)
        },
        placeholder = {
            Text(
                text = placeholder,
                style = GuideTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = GuideTheme.palette.onSurface.copy(
                    alpha = 0.6f,
                ),
            )
        },
        textStyle = GuideTheme.typography.bodyLargeBold,
        colors = TextFieldDefaults.colors(
            cursorColor = GuideTheme.palette.onSurface,
            focusedTextColor = GuideTheme.palette.onSurface,
            unfocusedTextColor = GuideTheme.palette.onSurface,
            focusedContainerColor = GuideTheme.palette.surface,
            unfocusedContainerColor = GuideTheme.palette.surface,
        ),
        shape = GuideTheme.shape.huge,
    )
}

private const val MAX_QUERY_LENGTH = 100
