package com.gm.ai.guidebook.ui.screen.home

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
class HomeViewModel @Inject constructor() : BaseViewModel() {

    val state = stateReducerFlow(
        initialState = HomeState(),
        reduceState = ::handleEvent,
    )
    val navigationEffect = Channel<HomeNavigationEffect>()

    private val list = listOf(
        Guide(
            id = 1,
            title = "C# Tutorial",
            description = "C# is an object-oriented, component-oriented programming language.",
            imageUrl = "https://images.ctfassets.net/23aumh6u8s0i/1IKVNqiLhNURzZXp652sEu/4379cfba19f0e19873af6074d3017f70/csharp",
        ),
        Guide(
            id = 2,
            title = "C++ Tutorial",
            description = "C++ is an object-oriented, component-oriented programming language.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/ISO_C%2B%2B_Logo.svg/800px-ISO_C%2B%2B_Logo.svg.png",
        ),
        Guide(
            id = 3,
            title = "Java Tutorial",
            description = "Java is an object-oriented, component-oriented programming language.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/uk/thumb/8/85/%D0%9B%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF_Java.png/250px-%D0%9B%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF_Java.png",
        ),
    )

    init {
        viewModelScope.launch {
            delay(1000)
            updateGuidesList()
        }
    }

    private fun updateGuidesList() = viewModelScope.launch(Dispatchers.IO) {
        val event = HomeEvent.UpdateGuidesList(list)
        state.handleEvent(event)
    }

    private fun filterBySearchQuery(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        val list = list.filter { it.title.contains(searchQuery, ignoreCase = true) }
        val event = HomeEvent.UpdateGuidesList(list)
        state.handleEvent(event)
    }

    private fun handleEvent(currentState: HomeState, event: HomeEvent): HomeState {
        event.handle(
            updateGuidesList = { list ->
                return currentState.copy(guides = list)
            },
            sendSearchQuery = { query ->
                filterBySearchQuery(query)
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
