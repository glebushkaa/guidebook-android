package com.gm.ai.guidebook

import android.app.Application
import com.google.firebase.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@HiltAndroidApp
class GuideApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        val tree = if (BuildConfig.DEBUG) Timber.DebugTree() else ReportingTree()
        Timber.plant(tree)
    }
}
