package com.example.digiclock.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.digiclock.R

class DigitDisplayManager {

    private val _digitDisplayLiveData = MutableLiveData<Map<Int, Int>>()
    val digitDisplayLiveData: LiveData<Map<Int, Int>> = _digitDisplayLiveData

    private val segmentIdList = listOf(
        R.id.segment_top,
        R.id.segment_top_left,
        R.id.segment_top_right,
        R.id.segment_middle,
        R.id.segment_bottom_left,
        R.id.segment_bottom_right,
        R.id.segment_bottom
    )

    private val digitMap = mapOf(
        R.id.segment_top to listOf(0, 2, 3, 5, 6, 7, 8, 9),
        R.id.segment_top_left to listOf(0, 4, 5, 6, 8, 9),
        R.id.segment_top_right to listOf(0, 1, 2, 3, 4, 7, 8, 9),
        R.id.segment_middle to listOf(2, 3, 4, 5, 6, 8, 9),
        R.id.segment_bottom_left to listOf(0, 2, 6, 8),
        R.id.segment_bottom_right to listOf(0, 1, 3, 4, 5, 6, 7, 8, 9),
        R.id.segment_bottom to listOf(0, 2, 3, 5, 6, 8)
    )

    fun onNewDigit(digit: Int) {
        val state = HashMap<Int, Int>()
        segmentIdList.forEach { id ->
            val colorRes = if (digitMap[id]!!.contains(digit)) {
                R.color.selected
            } else {
                R.color.unselected
            }
            state[id] = colorRes
        }
        _digitDisplayLiveData.postValue(state)
    }
}