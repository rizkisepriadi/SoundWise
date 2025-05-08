package com.app.sound_wise.ui.features.splash

import androidx.lifecycle.viewModelScope
import com.app.sound_wise.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel(){
    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            splashShowFlow.value = false
        }
    }
}