package com.gm.ai.guidebook.ui.screen.settings

import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.android.stateReducerFlow
import com.gm.ai.guidebook.domain.usecase.settings.CollectDarkModeUseCase
import com.gm.ai.guidebook.domain.usecase.settings.UpdateDarkModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateDarkModeUseCase: UpdateDarkModeUseCase,
    private val collectDarkModeUseCase: CollectDarkModeUseCase,
) : BaseViewModel() {

    val state = stateReducerFlow(
        initialState = SettingsState(),
        reduceState = ::handleEvent,
    )
    private var systemInDarkModeByDefault = false
    private var darkModeJob: Job? = null

    private fun collectDarkMode() = viewModelScope.launch(Dispatchers.IO) {
        collectDarkModeUseCase().getOrNull()?.collectLatest {
            val darkMode = it ?: systemInDarkModeByDefault
            val event = SettingsEvent.UpdateDarkModeSetting(darkMode)
            state.handleEvent(event)
        }
    }

    private fun updateDarkMode() = viewModelScope.launch(Dispatchers.IO) {
        val params = UpdateDarkModeUseCase.Params(enabled = !state.value.darkModeEnabled)
        updateDarkModeUseCase(params)
    }

    private fun handleEvent(currentState: SettingsState, event: SettingsEvent): SettingsState {
        event.handle(
            sendSystemDarkModeSetting = { isSystemInDarkModeByDefault ->
                darkModeJob?.cancel()
                systemInDarkModeByDefault = isSystemInDarkModeByDefault
                darkModeJob = collectDarkMode()
            },
            alterDarkMode = ::updateDarkMode,
            updateDarkModeSelector = { darkMode ->
                return currentState.copy(darkModeEnabled = darkMode)
            },
            sendNotificationsSettingUpdate = {
                return currentState.copy(notificationsChecked = it)
            },
        )
        return currentState
    }
}
