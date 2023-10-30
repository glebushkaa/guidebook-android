package com.gm.ai.guidebook.ui.screen.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.core.common.FIVE_HUNDRED_MILLIS
import com.gm.ai.guidebook.core.common.TWO_HUNDRED_MILLIS
import com.gm.ai.guidebook.ui.components.GuideSearch
import com.gm.ai.guidebook.ui.components.GuidesList
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Composable
fun FavoritesScreen(
    state: FavoritesState = FavoritesState(),
    onEvent: (FavoritesEvent) -> Unit = {},
) {
    LaunchedEffect(key1 = Unit) {
        onEvent(FavoritesEvent.AskFavorites)
    }

    FavoritesScreenContent(
        state = state,
        onEvent = onEvent,
    )
}

@Composable
private fun FavoritesScreenContent(
    state: FavoritesState = FavoritesState(),
    onEvent: (FavoritesEvent) -> Unit = {},
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideTheme.palette.background)
            .padding(GuideTheme.offset.regular),
        constraintSet = favoritesScreenDecoupledConstraints(
            mediumOffset = GuideTheme.offset.medium,
            regularOffset = GuideTheme.offset.regular,
            superGiganticOffset = GuideTheme.offset.superGigantic,
        ),
    ) {
        Text(
            modifier = Modifier.layoutId(TITLE_TEXT),
            text = stringResource(id = R.string.favorites),
            style = GuideTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
            ),
            color = GuideTheme.palette.onBackground,
        )
        GuideSearch(
            modifier = Modifier.layoutId(FAVORITES_SEARCH_BAR),
            value = state.searchQuery,
            onValueChanged = {
                val event = FavoritesEvent.SendSearchQuery(it)
                onEvent(event)
            },
        )
        AnimatedVisibility(
            modifier = Modifier.layoutId(NO_GUIDES_ITEM),
            visible = state.guides.isEmpty(),
            enter = fadeIn(animationSpec = tween(FIVE_HUNDRED_MILLIS.toInt())),
            exit = fadeOut(animationSpec = tween(TWO_HUNDRED_MILLIS.toInt())),
        ) {
            NoFavoriteItems()
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(bottom = GuideTheme.offset.regular)
                .layoutId(GUIDES_LIST),
            visible = state.guides.isNotEmpty(),
            enter = expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween(FIVE_HUNDRED_MILLIS.toInt()),
            ),
            exit = shrinkVertically(
                shrinkTowards = Alignment.Top,
                animationSpec = tween(FIVE_HUNDRED_MILLIS.toInt()),
            ),
        ) {
            GuidesList(
                list = state.guides,
                guideClicked = {
                    val event = FavoritesEvent.NavigateToDetailsScreen(it)
                    onEvent(event)
                },
            )
        }
    }
}

@Composable
private fun NoFavoriteItems(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .padding(bottom = GuideTheme.offset.medium)
                .size(100.dp),
            painter = painterResource(id = R.drawable.ic_not_like),
            contentDescription = null,
            tint = GuideTheme.palette.onBackground.copy(alpha = 0.8f),
        )
        Text(
            text = "No favorites found",
            style = GuideTheme.typography.bodyLarge,
            color = GuideTheme.palette.onBackground,
        )
    }
}

private fun favoritesScreenDecoupledConstraints(
    mediumOffset: Dp,
    regularOffset: Dp,
    superGiganticOffset: Dp,
) = ConstraintSet {
    val textTitle = createRefFor(TITLE_TEXT)
    val favoritesSearchBar = createRefFor(FAVORITES_SEARCH_BAR)
    val noGuidesItem = createRefFor(NO_GUIDES_ITEM)
    val guidesList = createRefFor(GUIDES_LIST)

    constrain(textTitle) {
        top.linkTo(anchor = parent.top, margin = mediumOffset)
        start.linkTo(anchor = parent.start)
    }

    constrain(favoritesSearchBar) {
        top.linkTo(anchor = textTitle.bottom, margin = mediumOffset)
        start.linkTo(anchor = parent.start)
        end.linkTo(anchor = parent.end)
    }

    constrain(noGuidesItem) {
        top.linkTo(anchor = favoritesSearchBar.bottom)
        start.linkTo(anchor = parent.start)
        end.linkTo(anchor = parent.end)
        bottom.linkTo(anchor = parent.bottom, margin = superGiganticOffset)
    }

    constrain(guidesList) {
        top.linkTo(anchor = favoritesSearchBar.bottom, margin = regularOffset)
        start.linkTo(anchor = parent.start)
        end.linkTo(anchor = parent.end)
    }
}

private const val FAVORITES_SEARCH_BAR = "home_search_bar"

private const val TITLE_TEXT = "title_text"
private const val NO_GUIDES_ITEM = "no_guides_item"
private const val GUIDES_LIST = "guides_list"
