package com.gm.ai.guidebook.ui.screen.favorites

import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.android.stateReducerFlow
import com.gm.ai.guidebook.model.Guide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@HiltViewModel
class FavoritesViewModel @Inject constructor() : BaseViewModel() {

    val state = stateReducerFlow(
        initialState = FavoritesState(),
        reduceState = ::handleEvent,
    )
    val navigationEffect = Channel<FavoritesNavigationEffect>()

    private val list = listOf<Guide>()

    init {
        viewModelScope.launch {
            delay(1000)
            updateGuidesList()
        }
    }

    private fun updateGuidesList() = viewModelScope.launch(Dispatchers.IO) {
        val event = FavoritesEvent.UpdateGuidesList(list)
        state.handleEvent(event)
    }

    private fun filterBySearchQuery(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        val list = list.filter { it.title.contains(searchQuery, ignoreCase = true) }
        val event = FavoritesEvent.UpdateGuidesList(list)
        state.handleEvent(event)
    }

    private fun handleEvent(currentState: FavoritesState, event: FavoritesEvent): FavoritesState {
        event.handle(
            updateGuidesList = { list ->
                return currentState.copy(guides = list)
            },
            sendSearchQuery = { query ->
                filterBySearchQuery(query)
                return currentState.copy(searchQuery = query)
            },
            navigateToDetailsScreen = { guideId ->
                val navEffect = FavoritesNavigationEffect.NavigateDetailsScreen(guideId)
                navigationEffect.trySend(navEffect)
            },
        )
        return currentState
    }
}
