package com.example.digiclock.arch

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digiclock.model.DigitDisplayManager
import com.example.digiclock.R
import com.example.digiclock.databinding.DigitNumberBinding
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel : ViewModel() {

    private val _playStateLiveData = MutableLiveData(false)
    val playStateLiveData = _playStateLiveData
    val tenthSecondStopwatchDisplayManager = DigitDisplayManager()
    val digitSecondStopwatchDisplayManager = DigitDisplayManager()
    val tenthMinuteStopwatchDisplayManager = DigitDisplayManager()
    val digitMinuteStopwatchDisplayManager = DigitDisplayManager()
    val tenthHoursStopwatchDisplayManager = DigitDisplayManager()
    val digitHoursStopwatchDisplayManager = DigitDisplayManager()

    val tenthSecondDigitalClockDisplayManager = DigitDisplayManager()
    val digitSecondDigitalClockDisplayManager = DigitDisplayManager()
    val tenthMinuteDigitalClockDisplayManager = DigitDisplayManager()
    val digitMinuteDigitalClockDisplayManager = DigitDisplayManager()
    val tenthHoursDigitalClockDisplayManager = DigitDisplayManager()
    val digitHoursDigitalClockDisplayManager = DigitDisplayManager()

    private val _secondDigitalClockLiveData = MutableLiveData(0)
    val secondDigitalClockLiveData = _secondDigitalClockLiveData

    private var index = 0

    init {
        setStopwatch()
    }

    private fun startCounting() {
        viewModelScope.launch {
            while (_playStateLiveData.value!!) {
                val second = index % 60
                val minute = (index / 60) % 60
                val hour = index / 3600
                setStopwatch(hour, minute, second)
                index++
                delay(1000)
            }
        }
    }

    fun getSystemClock() {
        viewModelScope.launch {
            while (true) {
                val calendar = Calendar.getInstance()
                val calendarSecond = calendar.get(Calendar.SECOND)
                _secondDigitalClockLiveData.value = calendarSecond
                setDigitalClock(
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE),
                    calendarSecond
                )
                delay(1000)
            }
        }
    }

    private fun setStopwatch(hour: Int = 0, minute: Int = 0, second: Int = 0) {
        tenthMinuteStopwatchDisplayManager.onNewDigit(minute / 10)
        digitMinuteStopwatchDisplayManager.onNewDigit(minute % 10)
        tenthSecondStopwatchDisplayManager.onNewDigit(second / 10)
        digitSecondStopwatchDisplayManager.onNewDigit(second % 10)
        tenthHoursStopwatchDisplayManager.onNewDigit(hour / 10)
        digitHoursStopwatchDisplayManager.onNewDigit(hour % 10)
    }

    private fun setDigitalClock(hour: Int = 0, minute: Int = 0, second: Int = 0) {
        tenthMinuteDigitalClockDisplayManager.onNewDigit(minute / 10)
        digitMinuteDigitalClockDisplayManager.onNewDigit(minute % 10)
        tenthSecondDigitalClockDisplayManager.onNewDigit(second / 10)
        digitSecondDigitalClockDisplayManager.onNewDigit(second % 10)
        tenthHoursDigitalClockDisplayManager.onNewDigit(hour / 10)
        digitHoursDigitalClockDisplayManager.onNewDigit(hour % 10)
    }

    fun switchState() {
        _playStateLiveData.value = !_playStateLiveData.value!!
        if (_playStateLiveData.value!!) {
            startCounting()
        }
    }

    fun reset() {
        index = 0
        _playStateLiveData.value = false
        setStopwatch()
    }

    fun setupLayoutWithNewDigit(binding: DigitNumberBinding, map: Map<Int, Int>) {
        styleCardView(binding.segmentTop.root, map[R.id.segment_top]!!)
        styleCardView(binding.segmentTopLeft.root, map[R.id.segment_top_left]!!)
        styleCardView(binding.segmentTopRight.root, map[R.id.segment_top_right]!!)
        styleCardView(binding.segmentMiddle.root, map[R.id.segment_middle]!!)
        styleCardView(binding.segmentBottomLeft.root, map[R.id.segment_bottom_left]!!)
        styleCardView(binding.segmentBottomRight.root, map[R.id.segment_bottom_right]!!)
        styleCardView(binding.segmentBottom.root, map[R.id.segment_bottom]!!)
    }

    private fun styleCardView(materialCardView: MaterialCardView, @ColorRes colorRes: Int) {
        materialCardView.apply {
            val resolvedColor = ContextCompat.getColor(context, colorRes)
            setBackgroundColor(resolvedColor)
        }
    }
}