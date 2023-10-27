package com.gm.ai.guidebook.di

import com.gm.ai.guidebook.data.repository.GuideRepositoryImpl
import com.gm.ai.guidebook.domain.repository.GuideRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGuideRepository(guideRepositoryImpl: GuideRepositoryImpl): GuideRepository
}
