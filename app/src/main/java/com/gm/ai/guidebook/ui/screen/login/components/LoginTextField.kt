package com.gm.ai.guidebook.ui.screen.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.gm.ai.guidebook.R
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
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var textHidden by rememberSaveable {
        mutableStateOf(false)
    }

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
        visualTransformation = if (
            keyboardType != KeyboardType.Password ||
            textHidden
        ) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = GuideTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = GuideTheme.palette.onSurface.copy(
                    alpha = 0.6f,
                ),
            )
        },
        trailingIcon = {
            val imageResId = if (textHidden) {
                R.drawable.ic_visibility_off
            } else {
                R.drawable.ic_visibility
            }

            IconButton(onClick = { textHidden = !textHidden }) {
                Icon(
                    painter = painterResource(imageResId),
                    contentDescription = null,
                    tint = GuideTheme.palette.onSurface,
                )
            }
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
