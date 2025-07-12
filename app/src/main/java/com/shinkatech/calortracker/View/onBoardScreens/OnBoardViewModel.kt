package com.shinkatech.calortracker.View.onBoardScreens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class OnBoardViewModel @Inject constructor() : ViewModel() {
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    fun next() {
        _currentIndex.value = (_currentIndex.value + 1).coerceAtMost(3)
    }

    fun previous() {
        _currentIndex.value = (_currentIndex.value - 1).coerceAtLeast(0)
    }

    fun setIndex(index: Int) {
        _currentIndex.value = index
    }
}