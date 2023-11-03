package com.gm.ai.guidebook.ui.screen.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideTheme.palette.background)
            .padding(GuideTheme.offset.regular),
    ) {
        Text(
            text = stringResource(id = R.string.favorites),
            style = GuideTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
            ),
            color = GuideTheme.palette.onBackground,
        )
        GuideSearch(
            value = state.searchQuery,
            onValueChanged = {
                val event = FavoritesEvent.SendSearchQuery(it)
                onEvent(event)
            },
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            this@Column.AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = GuideTheme.offset.superGigantic),
                visible = state.guides.isEmpty(),
                enter = fadeIn(animationSpec = tween(FIVE_HUNDRED_MILLIS.toInt())),
                exit = fadeOut(animationSpec = tween(TWO_HUNDRED_MILLIS.toInt())),
            ) {
                NoFavoriteItems()
            }
            this@Column.AnimatedVisibility(
                modifier = Modifier
                    .padding(top = GuideTheme.offset.regular)
                    .align(Alignment.TopCenter),
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
                .size(
                    dimensionResource(R.dimen.no_favorite_items_icon_size)
                ),
            painter = painterResource(id = R.drawable.ic_not_like),
            contentDescription = null,
            tint = GuideTheme.palette.onBackground.copy(alpha = NO_FAVORITE_ITEMS_ICON_ALPHA),
        )
        Text(
            text = stringResource(R.string.no_favorites_found),
            style = GuideTheme.typography.bodyLarge,
            color = GuideTheme.palette.onBackground,
        )
    }
}

private const val NO_FAVORITE_ITEMS_ICON_ALPHA = 0.8f
