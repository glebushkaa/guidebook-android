package com.gm.ai.guidebook.ui.screen.login

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

sealed class LoginScreenEvent {

    data object LoginClicked : LoginScreenEvent()
    data object DisableLoginButton : LoginScreenEvent()
    data object EnableLoginButton : LoginScreenEvent()
    data object SwapLoginMode : LoginScreenEvent()
    data class UpdateEmailTextField(val email: String) : LoginScreenEvent()
    data class UpdateUsernameTextField(val username: String) : LoginScreenEvent()
    data class UpdatePasswordTextField(val password: String) : LoginScreenEvent()

    inline fun handle(
        sendNewLoginMode: () -> Unit = {},
        updateEmailTextField: (String) -> Unit = {},
        updateUsernameTextField: (String) -> Unit = {},
        updatePasswordTextField: (String) -> Unit = {},
        disableLoginButton: () -> Unit = {},
        enableLoginButton: () -> Unit = {},
        loginClicked: () -> Unit = {},
    ) {
        when (this) {
            is SwapLoginMode -> sendNewLoginMode()
            is UpdateEmailTextField -> updateEmailTextField(email)
            is UpdatePasswordTextField -> updatePasswordTextField(password)
            is UpdateUsernameTextField -> updateUsernameTextField(username)
            DisableLoginButton -> disableLoginButton()
            EnableLoginButton -> enableLoginButton()
            LoginClicked -> loginClicked()
        }
    }
}
