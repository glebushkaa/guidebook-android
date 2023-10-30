package com.gm.ai.guidebook.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.android.stateReducerFlow
import com.gm.ai.guidebook.domain.usecase.auth.GetUserUseCase
import com.gm.ai.guidebook.domain.usecase.guides.GetGuideDetailsByIdUseCase
import com.gm.ai.guidebook.model.emptyGuideDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getGuideDetailsByIdUseCase: GetGuideDetailsByIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    val state = stateReducerFlow(
        initialState = DetailsState(guide = emptyGuideDetails()),
        reduceState = ::handleEvent,
    )
    val navigationEffect = Channel<DetailsNavigationEffect>()

    private val guideId = savedStateHandle["guideId"] ?: ""

    init {
        getGuideDetails()
    }

    private fun getGuideDetails() = viewModelScope.launch(Dispatchers.IO) {
        val params = GetGuideDetailsByIdUseCase.Params(guideId)
        val details = getGuideDetailsByIdUseCase(params).getOrNull() ?: return@launch
        val event = DetailsEvent.UpdateGuideDetails(details)
        state.handleEvent(event)
    }

    private fun handleEvent(currentState: DetailsState, event: DetailsEvent): DetailsState {
        event.handle(
            backEvent = {
                val navEffect = DetailsNavigationEffect.NavigateBack
                navigationEffect.trySend(navEffect)
            },
            updateGuideDetails = { guideDetails ->
                return currentState.copy(guide = guideDetails)
            },
        )
        return currentState
    }
}
