package com.gm.ai.guidebook.domain.usecase.settings

import com.gm.ai.guidebook.domain.SettingsDataStore
import com.gm.ai.guidebook.domain.usecase.core.ResultNoneParamsUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

class CollectDarkModeUseCase @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    useCaseLogger: UseCaseLogger,
) : ResultNoneParamsUseCase<Flow<Boolean?>>(useCaseLogger) {

    override fun invoke() = runCatching { settingsDataStore.darkModeFlow }
}
