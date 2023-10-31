package com.gm.ai.guidebook.ui.screen.steps

import com.gm.ai.guidebook.model.Step

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/31/2023
 */

sealed class StepsEvent {
    data class UpdateSteps(val steps: List<Step>) : StepsEvent()
}