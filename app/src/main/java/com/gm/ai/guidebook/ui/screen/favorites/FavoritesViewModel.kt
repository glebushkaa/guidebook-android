package com.gm.ai.guidebook.ui.screen.favorites

import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.android.stateReducerFlow
import com.gm.ai.guidebook.domain.usecase.favorite.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
) : BaseViewModel() {

    val state = stateReducerFlow(
        initialState = FavoritesState(),
        reduceState = ::handleEvent,
    )
    val navigationEffect = Channel<FavoritesNavigationEffect>()

    private fun fetchFavorites() = viewModelScope.launch(Dispatchers.IO) {
        val favorites = getFavoritesUseCase().getOrNull() ?: emptyList()
        val event = FavoritesEvent.UpdateGuidesList(favorites)
        state.handleEvent(event)
    }

    private fun handleEvent(currentState: FavoritesState, event: FavoritesEvent): FavoritesState {
        event.handle(
            updateGuidesList = { list ->
                return currentState.copy(guides = list)
            },
            navigateToDetailsScreen = { guideId ->
                val navEffect = FavoritesNavigationEffect.NavigateDetailsScreen(guideId)
                navigationEffect.trySend(navEffect)
            },
            sendSearchQuery = { query ->
                return currentState.copy(searchQuery = query)
            },
            askFavorites = { fetchFavorites() },
        )
        return currentState
    }
}
