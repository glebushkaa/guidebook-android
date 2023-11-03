package com.gm.ai.guidebook.ui.screen.login

import com.gm.ai.guidebook.domain.usecase.auth.LoginField

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

sealed class LoginScreenEvent {

    data object LoginClicked : LoginScreenEvent()
    data object SwapLoginMode : LoginScreenEvent()
    data object DisableLoginButton : LoginScreenEvent()
    data object EnableLoginButton : LoginScreenEvent()
    data class UpdateEmailTextField(val email: String) : LoginScreenEvent()
    data class UpdateUsernameTextField(val username: String) : LoginScreenEvent()
    data class UpdatePasswordTextField(val password: String) : LoginScreenEvent()
    data class SendTextFieldMessage(
        val field: LoginField, val message: String
    ) : LoginScreenEvent()
}
