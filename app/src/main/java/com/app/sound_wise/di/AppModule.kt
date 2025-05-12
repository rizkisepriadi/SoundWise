package com.app.sound_wise.di

import com.app.sound_wise.data.repositories.RuleRepository
import com.app.sound_wise.ui.features.home.HomeViewModel
import com.app.sound_wise.ui.features.loading.LoadingViewModel
import com.app.sound_wise.ui.features.splash.SplashViewModel
import com.app.sound_wise.ui.features.question.QuestionsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RuleRepository(androidContext()) }
    viewModel{ QuestionsViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SplashViewModel() }
    viewModel { LoadingViewModel() }
}