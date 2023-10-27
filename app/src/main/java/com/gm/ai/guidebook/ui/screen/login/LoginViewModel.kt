package com.gm.ai.guidebook.ui.screen.login

import com.gm.ai.guidebook.core.android.BaseViewModel
import com.gm.ai.guidebook.core.android.stateReducerFlow
import com.gm.ai.guidebook.domain.usecase.auth.SignInUseCase
import com.gm.ai.guidebook.domain.usecase.auth.SignUpUseCase
import com.gm.ai.guidebook.log.debug
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
) : BaseViewModel() {

    val state = stateReducerFlow(
        initialState = LoginScreenState(),
        reduceState = ::handleEvent,
    )
    val navigationEffect = Channel<LoginNavigationEffect>()

    private fun swapLoginMode() = when (state.value.loginMode) {
        LoginMode.SIGN_UP -> LoginMode.SIGN_IN
        LoginMode.SIGN_IN -> LoginMode.SIGN_UP
    }

    private fun validateInput(
        email: String = state.value.email,
        username: String = state.value.username,
        password: String = state.value.password,
    ) = viewModelScope.launch(Dispatchers.IO) {
        val isEmailValid = validateEmail(email)
        val isUsernameValid = validateUsername(username)
        val isPasswordValid = validatePassword(password)
        val isInputValid = isEmailValid && isUsernameValid && isPasswordValid
        val event = if (isInputValid) {
            LoginScreenEvent.EnableLoginButton
        } else {
            LoginScreenEvent.DisableLoginButton
        }
        state.handleEvent(event)
    }

    private fun validateEmail(email: String): Boolean {
        val email = email.trim()
        return email.contains("@") && email.length > MIN_EMAIL_LENGTH
    }

    private fun validateUsername(username: String): Boolean {
        val username = username.trim()
        return username.length > MIN_USERNAME_LENGTH &&
            !username.contains("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())
    }

    private fun validatePassword(password: String): Boolean {
        val password = password.trim()
        return password.length > MIN_PASSWORD_LENGTH /*&&
            password.contains("[A-Z]".toRegex()) &&
            password.contains("[a-z]".toRegex()) &&
            password.contains("[0-9]".toRegex())*/
    }

    private fun login() = viewModelScope.launch(Dispatchers.IO) {
        val email = state.value.email.trim()
        val username = state.value.username.trim()
        val password = state.value.password.trim()
        when (state.value.loginMode) {
            LoginMode.SIGN_UP -> {
                val params = SignUpUseCase.Params(
                    email = email,
                    username = username,
                    password = password,
                )
                signUpUseCase(params)
            }

            LoginMode.SIGN_IN -> {
                val params = SignInUseCase.Params(email = email, password = password)
                signInUseCase(params)
            }
        }.onSuccess {
            val navEffect = LoginNavigationEffect.NavigateToHomeScreen
            navigationEffect.trySend(navEffect)
        }.onFailure {
            debug("LoginViewModel") {
                "login() failed with error: $it"
            }
        }
    }

    private fun handleEvent(
        currentState: LoginScreenState,
        event: LoginScreenEvent,
    ): LoginScreenState {
        event.handle(
            sendNewLoginMode = {
                val newMode = swapLoginMode()
                return currentState.copy(loginMode = newMode)
            },
            updateEmailTextField = { email ->
                validateInput(email = email)
                return currentState.copy(email = email)
            },
            updateUsernameTextField = { username ->
                validateInput(username = username)
                return currentState.copy(username = username)
            },
            updatePasswordTextField = { password ->
                validateInput(password = password)
                return currentState.copy(password = password)
            },
            disableLoginButton = {
                return currentState.copy(loginButtonEnabled = false)
            },
            enableLoginButton = {
                return currentState.copy(loginButtonEnabled = true)
            },
            loginClicked = ::login,
        )
        return currentState
    }

    private companion object {
        const val MIN_EMAIL_LENGTH = 10
        const val MIN_USERNAME_LENGTH = 4
        const val MIN_PASSWORD_LENGTH = 8
    }
}
