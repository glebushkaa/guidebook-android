package com.gm.ai.guidebook.data.usecase

import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import com.gm.ai.guidebook.log.error
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

class UseCaseLoggerImpl @Inject constructor() : UseCaseLogger {

    override fun logException(tag: String, throwable: Throwable) {
        error(tag, throwable)
    }
}
