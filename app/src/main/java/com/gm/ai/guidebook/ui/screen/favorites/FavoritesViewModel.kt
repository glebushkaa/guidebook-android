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

    private fun searchFavorites(
        query: String = state.value.searchQuery,
    ) = viewModelScope.launch(Dispatchers.IO) {
        val params = GetFavoritesUseCase.Params(query = query)
        val favorites = getFavoritesUseCase(params).getOrNull() ?: emptyList()
        val event = FavoritesEvent.UpdateGuidesList(favorites)
        state.handleEvent(event)
    }

    private fun handleEvent(currentState: FavoritesState, event: FavoritesEvent): FavoritesState {
        when (event) {
            is FavoritesEvent.UpdateGuidesList -> {
                return currentState.copy(guides = event.list)
            }

            is FavoritesEvent.NavigateToDetailsScreen -> {
                val navEffect = FavoritesNavigationEffect.NavigateDetailsScreen(event.guideId)
                navigationEffect.trySend(navEffect)
            }

            is FavoritesEvent.SendSearchQuery -> {
                searchFavorites(query = event.query)
                return currentState.copy(searchQuery = event.query)
            }

            FavoritesEvent.AskFavorites -> searchFavorites()
        }
        return currentState
    }
}
