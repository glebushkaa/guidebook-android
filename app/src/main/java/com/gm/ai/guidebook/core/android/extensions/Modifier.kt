package com.gm.ai.guidebook.core.android.extensions

import androidx.compose.ui.Modifier

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier,
) = if (condition) then(modifier()) else this
