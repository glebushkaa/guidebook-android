package com.gm.ai.guidebook.ui.screen.steps

import com.gm.ai.guidebook.model.Step

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/31/2023
 */

data class StepsState(
    val steps: List<Step> = emptyList(),
)