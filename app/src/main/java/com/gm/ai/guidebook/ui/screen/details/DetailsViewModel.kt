package com.gm.ai.guidebook.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.android.stateReducerFlow
import com.gm.ai.guidebook.domain.usecase.favorite.AddGuideToFavoriteUseCase
import com.gm.ai.guidebook.domain.usecase.favorite.RemoveGuideFromFavoriteUseCase
import com.gm.ai.guidebook.domain.usecase.guides.GetGuideDetailsByIdUseCase
import com.gm.ai.guidebook.domain.usecase.guides.GetGuideStepsUseCase
import com.gm.ai.guidebook.model.GuideDetails
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
    private val removeGuideFromFavoriteUseCase: RemoveGuideFromFavoriteUseCase,
    private val addGuideToFavoriteUseCase: AddGuideToFavoriteUseCase,
    private val getGuideStepsUseCase: GetGuideStepsUseCase,
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
        fetchGuideSteps()
    }

    private fun fetchGuideSteps() = viewModelScope.launch(Dispatchers.IO) {
        val params = GetGuideStepsUseCase.Params(guideId = guideId)
        val list = getGuideStepsUseCase(params).getOrNull() ?: emptyList()
        val event = if (list.isNotEmpty()) {
            DetailsEvent.ShowStepsButton
        } else {
            DetailsEvent.HideStepsButton
        }
        state.handleEvent(event)
    }

    private fun getGuideDetails() = viewModelScope.launch(Dispatchers.IO) {
        val params = GetGuideDetailsByIdUseCase.Params(guideId)
        val details = getGuideDetailsByIdUseCase(params).getOrNull() ?: return@launch
        val event = DetailsEvent.UpdateGuideDetails(details)
        state.handleEvent(event)
    }

    private fun handleLikeClickedEvent(
        guide: GuideDetails,
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (guide.favorite) {
            val params = RemoveGuideFromFavoriteUseCase.Params(guide.id)
            removeGuideFromFavoriteUseCase(params)
        } else {
            val params = AddGuideToFavoriteUseCase.Params(guide.id)
            addGuideToFavoriteUseCase(params)
        }
    }

    private fun handleEvent(currentState: DetailsState, event: DetailsEvent): DetailsState {
        when (event) {
            is DetailsEvent.BackEvent -> {
                val navEffect = DetailsNavigationEffect.NavigateBack
                navigationEffect.trySend(navEffect)
            }

            is DetailsEvent.UpdateGuideDetails -> {
                return currentState.copy(guide = event.guideDetails)
            }

            DetailsEvent.LikeClicked -> {
                val currentGuide = currentState.guide
                val newGuide = currentState.guide.copy(favorite = !currentGuide.favorite)
                handleLikeClickedEvent(currentGuide)
                return currentState.copy(guide = newGuide)
            }

            DetailsEvent.OpenSteps -> {
                val navEffect = DetailsNavigationEffect.NavigateSteps(currentState.guide.id)
                navigationEffect.trySend(navEffect)
            }

            DetailsEvent.HideStepsButton -> {
                return currentState.copy(stepsButtonVisible = false)
            }

            DetailsEvent.ShowStepsButton -> {
                return currentState.copy(stepsButtonVisible = true)
            }
        }
        return currentState
    }
}
