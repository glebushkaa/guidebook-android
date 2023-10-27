package com.gm.ai.guidebook.data.mapper

import com.gm.ai.guidebook.data.network.dto.auth.UserDto
import com.gm.ai.guidebook.model.User

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/28/2023
 */

fun UserDto.toUser(): User {
    return User(
        id = id ?: "",
        username = username ?: "",
        email = email ?: "",
    )
}
