package com.gm.ai.guidebook.ui.screen.info

import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.android.stateReducerFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@HiltViewModel
class DetailsViewModel @Inject constructor() : BaseViewModel() {

    val state = stateReducerFlow(
        initialState = DetailsState(),
        reduceState = ::handleEvent,
    )
    val navigationEffect = Channel<DetailsNavigationEffect>()

    private fun handleEvent(currentState: DetailsState, event: DetailsEvent): DetailsState {
        event.handle(
            backEvent = {
                val navEffect = DetailsNavigationEffect.NavigateBack
                navigationEffect.trySend(navEffect)
            },
        )
        return currentState
    }
}
