package com.app.sound_wise.ui.features.loading

import androidx.lifecycle.viewModelScope
import com.app.sound_wise.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoadingViewModel : BaseViewModel(){
    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            splashShowFlow.value = false
        }
    }
}