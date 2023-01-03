package com.example.digiclock

import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.digiclock.databinding.ActivityMainBinding
import com.example.digiclock.databinding.DigitNumberBinding
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.digitSecondDisplayManager.digitDisplayLiveData.observe(this) {map ->
            setupLayoutWithNewDigit(binding.secondDigit,map)
        }

        viewModel.tenthSecondDisplayManager.digitDisplayLiveData.observe(this) {map ->
            setupLayoutWithNewDigit(binding.secondTenth,map)
        }

        viewModel.digitMinuteDisplayManager.digitDisplayLiveData.observe(this) { map ->
            setupLayoutWithNewDigit(binding.minuteDigit,map)
        }

        viewModel.tenthMinuteDisplayManager.digitDisplayLiveData.observe(this) {map ->
            setupLayoutWithNewDigit(binding.minuteTenth,map)
        }

        viewModel.tenthHoursDisplayManager.digitDisplayLiveData.observe(this) {map ->
            setupLayoutWithNewDigit(binding.hourTenth,map)
        }

        viewModel.digitHoursDisplayManager.digitDisplayLiveData.observe(this) {map ->
            setupLayoutWithNewDigit(binding.hourDigit,map)
        }

        viewModel.playStateLiveData.observe(this) {
            binding.start.apply {
                if(it) {
                    setImageResource(R.drawable.ic_baseline_pause)
                } else {
                    setImageResource(R.drawable.ic_baseline_play_arrow)
                }
            }
        }

        binding.start.setOnClickListener {
            viewModel.switchState()
        }

        binding.reset.setOnClickListener {
            viewModel.reset()
        }
    }

    private fun setupLayoutWithNewDigit(binding: DigitNumberBinding, map: Map<Int, Int>){
        styleCardView(binding.segmentTop.root,map[R.id.segment_top]!!)
        styleCardView(binding.segmentTopLeft.root,map[R.id.segment_top_left]!!)
        styleCardView(binding.segmentTopRight.root,map[R.id.segment_top_right]!!)
        styleCardView(binding.segmentMiddle.root,map[R.id.segment_middle]!!)
        styleCardView(binding.segmentBottomLeft.root,map[R.id.segment_bottom_left]!!)
        styleCardView(binding.segmentBottomRight.root,map[R.id.segment_bottom_right]!!)
        styleCardView(binding.segmentBottom.root,map[R.id.segment_bottom]!!)

    }

    private fun styleCardView(materialCardView: MaterialCardView, @ColorRes colorRes: Int) {
        materialCardView.apply {
            val resolvedColor = ContextCompat.getColor(context, colorRes)
            setBackgroundColor(resolvedColor)
        }
    }
}