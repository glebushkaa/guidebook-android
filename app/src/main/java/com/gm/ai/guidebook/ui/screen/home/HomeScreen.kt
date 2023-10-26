package com.gm.ai.guidebook.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gm.ai.guidebook.ui.theme.GuideBookTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Composable
fun HomeScreen() {
    HomeScreenContent()
}

@Composable
private fun HomeScreenContent(

) {

}

@Preview
@Composable
private fun HomeScreenPreview() {
    GuideBookTheme(darkTheme = true) {
        HomeScreen()
    }
}
