package com.example.digiclock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _playStateLiveData = MutableLiveData(false)
    val playStateLiveData = _playStateLiveData
    val tenthSecondDisplayManager = DigitDisplayManager()
    val digitSecondDisplayManager = DigitDisplayManager()
    val tenthMinuteDisplayManager = DigitDisplayManager()
    val digitMinuteDisplayManager = DigitDisplayManager()
    val tenthHoursDisplayManager = DigitDisplayManager()
    val digitHoursDisplayManager = DigitDisplayManager()
    private var index = 0

    init {
        setDigitalClock()
    }

    private fun startCounting() {
        viewModelScope.launch {
            while (_playStateLiveData.value!!) {
                val second = index % 60
                val minute = (index / 60) % 60
                val hour = index / 3600
                setDigitalClock(hour,minute, second)
                index ++
                delay(1000)
            }
        }
    }

    private fun setDigitalClock(hour: Int = 0,minute: Int = 0,second: Int = 0) {
        tenthMinuteDisplayManager.onNewDigit(minute / 10)
        digitMinuteDisplayManager.onNewDigit(minute % 10)
        tenthSecondDisplayManager.onNewDigit(second / 10)
        digitSecondDisplayManager.onNewDigit( second % 10)
        tenthHoursDisplayManager.onNewDigit(hour / 10)
        digitHoursDisplayManager.onNewDigit(hour % 10)
    }

    fun switchState() {
        _playStateLiveData.value = !_playStateLiveData.value!!
        if(_playStateLiveData.value!!){
            startCounting()
        }
    }

    fun reset() {
        index = 0
        _playStateLiveData.value = false
        setDigitalClock()
    }
}