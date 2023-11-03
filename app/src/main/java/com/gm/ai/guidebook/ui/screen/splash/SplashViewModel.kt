package com.gm.ai.guidebook.ui.screen.splash

import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.common.ONE_SECOND
import com.gm.ai.guidebook.domain.usecase.auth.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/28/2023
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : BaseViewModel() {

    val navigationEffect = Channel<SplashNavigationEffect>()

    init {
        checkUserExistence()
    }

    private fun checkUserExistence() = viewModelScope.launch(Dispatchers.IO) {
        delay(ONE_SECOND)
        getUserUseCase().onSuccess {
            val effect = SplashNavigationEffect.NavigateToHome
            navigationEffect.trySend(effect)
        }.onFailure {
            val effect = SplashNavigationEffect.NavigateToLogin
            navigationEffect.trySend(effect)
        }
    }
}
