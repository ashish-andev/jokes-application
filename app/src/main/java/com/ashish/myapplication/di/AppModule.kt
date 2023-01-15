package com.ashish.myapplication.di

import com.ashish.myapplication.presentation.jokes.JokesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { JokesViewModel(get(), get(), get(), get()) }

    single { createGetJokeFromNetworkUseCase(get()) }

    single { createAddJokesToCacheUseCase(get()) }

    single { createGetCachedJokesUseCase(get()) }

    single { createJokesRepository(get(), get()) }

    single { createNetworkConnectivity(get()) }
}