package com.gm.ai.guidebook.ui.screen.home

import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.android.stateReducerFlow
import com.gm.ai.guidebook.domain.usecase.guides.GetGuidesByQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGuidesByQueryUseCase: GetGuidesByQueryUseCase,
) : BaseViewModel() {

    val state = stateReducerFlow(
        initialState = HomeState(),
        reduceState = ::handleEvent,
    )
    val navigationEffect = Channel<HomeNavigationEffect>()

    init {
        fetchGuidesByQuery()
    }

    private fun fetchGuidesByQuery(
        query: String = "",
    ) = viewModelScope.launch(Dispatchers.IO) {
        val params = GetGuidesByQueryUseCase.Params(query)
        val list = getGuidesByQueryUseCase(params).getOrNull() ?: emptyList()
        val event = HomeEvent.UpdateGuidesList(list)
        state.handleEvent(event)
    }

    private fun handleEvent(currentState: HomeState, event: HomeEvent): HomeState {
        event.handle(
            updateGuidesList = { list ->
                return currentState.copy(guides = list)
            },
            sendSearchQuery = { query ->
                fetchGuidesByQuery(query)
                return currentState.copy(searchQuery = query)
            },
            navigateToDetailsScreen = { guideId ->
                val navEffect = HomeNavigationEffect.NavigateDetailsScreen(guideId)
                navigationEffect.trySend(navEffect)
            },
        )
        return currentState
    }
}
