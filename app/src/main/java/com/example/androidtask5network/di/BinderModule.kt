package com.example.androidtask5network.di

import com.example.androidtask5network.feature_catlist.domain.usecase.GetCatsUseCase
import com.example.androidtask5network.feature_catlist.domain.usecase.GetCatsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindGetCatsUseCase(
        getCatsUseCase: GetCatsUseCaseImpl
    ): GetCatsUseCase
}
