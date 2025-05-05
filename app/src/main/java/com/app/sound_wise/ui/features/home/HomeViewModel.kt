package com.app.sound_wise.ui.features.home

import com.app.sound_wise.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : BaseViewModel() {
    private val _message = MutableStateFlow("Hello from ViewModel!")
    val message: StateFlow<String> = _message
}