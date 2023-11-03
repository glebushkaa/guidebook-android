package com.gm.ai.guidebook.ui.screen.steps

import androidx.lifecycle.SavedStateHandle
import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.android.stateReducerFlow
import com.gm.ai.guidebook.domain.usecase.guides.GetGuideStepsUseCase
import com.gm.ai.guidebook.model.Step
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/31/2023
 */

@HiltViewModel
class StepsViewModel @Inject constructor(
    private val getGuideStepsUseCase: GetGuideStepsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val state = stateReducerFlow(
        initialState = StepsState(),
        reduceState = ::handle
    )

    private val guideId = savedStateHandle["guideId"] ?: ""

    init {
        fetchGuideSteps()
    }

    private fun fetchGuideSteps() = viewModelScope.launch(Dispatchers.IO) {
        val params = GetGuideStepsUseCase.Params(guideId = guideId)
        val list = getGuideStepsUseCase(params).getOrNull() ?: emptyList()
        val event = StepsEvent.UpdateSteps(steps = list)
        state.handleEvent(event)
    }

    private fun handle(currentState: StepsState, event: StepsEvent): StepsState {
        return when (event) {
            is StepsEvent.UpdateSteps -> {
                currentState.copy(steps = event.steps)
            }
        }
    }
}