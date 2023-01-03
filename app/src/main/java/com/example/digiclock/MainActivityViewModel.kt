package com.example.digiclock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivityViewModel : ViewModel() {

    val tenthSecondDisplayManager = DigitDisplayManager()
    val digitSecondDisplayManager = DigitDisplayManager()
    val tenthMinuteDisplayManager = DigitDisplayManager()
    val digitMinuteDisplayManager = DigitDisplayManager()

    fun startCounting() {
        viewModelScope.launch {
            var index = 0
            while (true) {
                val hour = index / 60
                val second = index % 60
                tenthMinuteDisplayManager.onNewDigit(hour / 10)
                digitMinuteDisplayManager.onNewDigit(hour % 10)
                tenthSecondDisplayManager.onNewDigit(second / 10)
                digitSecondDisplayManager.onNewDigit( second % 10)
                index ++
                delay(1000)
            }
        }
    }
}