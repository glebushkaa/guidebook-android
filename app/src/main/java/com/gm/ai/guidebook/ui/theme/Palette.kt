package com.gm.ai.guidebook.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

data class Palette(
    val primary: Color = Color.Blue,
    val secondary: Color = Color.Green,
    val tertiary: Color = Color.Red,
    val background: Color = BackgroundWhite,
    val surface: Color = Color.White,
    val error: Color = Color.Red,
    val onPrimary: Color = Color.White,
    val onSecondary: Color = Color.White,
    val onTertiary: Color = Color.White,
    val onBackground: Color = Color.Black,
    val onSurface: Color = Color.Black,
    val onError: Color = Color.Red,
)

val darkPalette = Palette(
    primary = DarkBlue,
    secondary = DarkGreen,
    tertiary = DarkRed,
    background = DarkGrey,
    surface = SurfaceBlack,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Red,
)

val lightPalette = Palette(
    primary = Color.Blue,
    secondary = Color.Green,
    tertiary = Color.Red,
    background = BackgroundWhite,
    surface = Color.White,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.Red,
)
