package com.example.digiclock.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.digiclock.arch.MainActivityViewModel
import com.example.digiclock.R
import com.example.digiclock.databinding.FragmentStopwatchBinding

class StopWatchFragment : Fragment() {
    private val viewModel: MainActivityViewModel by activityViewModels()
    private var _binding: FragmentStopwatchBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.digitSecondStopwatchDisplayManager.digitDisplayLiveData.observe(viewLifecycleOwner) { map ->
            viewModel.setupLayoutWithNewDigit(binding.secondDigit, map)
        }

        viewModel.tenthSecondStopwatchDisplayManager.digitDisplayLiveData.observe(viewLifecycleOwner) { map ->
            viewModel.setupLayoutWithNewDigit(binding.secondTenth, map)
        }

        viewModel.digitMinuteStopwatchDisplayManager.digitDisplayLiveData.observe(viewLifecycleOwner) { map ->
            viewModel.setupLayoutWithNewDigit(binding.minuteDigit, map)
        }

        viewModel.tenthMinuteStopwatchDisplayManager.digitDisplayLiveData.observe(viewLifecycleOwner) { map ->
            viewModel.setupLayoutWithNewDigit(binding.minuteTenth, map)
        }

        viewModel.tenthHoursStopwatchDisplayManager.digitDisplayLiveData.observe(viewLifecycleOwner) { map ->
            viewModel.setupLayoutWithNewDigit(binding.hourTenth, map)
        }

        viewModel.digitHoursStopwatchDisplayManager.digitDisplayLiveData.observe(viewLifecycleOwner) { map ->
            viewModel.setupLayoutWithNewDigit(binding.hourDigit, map)
        }

        binding.start.setOnClickListener {
            viewModel.switchState()
            binding.start.apply {
                if (viewModel.playStateLiveData.value!!)
                    setImageResource(R.drawable.ic_baseline_pause)
                else
                    setImageResource(R.drawable.ic_baseline_play_arrow)
            }
        }

        binding.reset.setOnClickListener {
            viewModel.reset()
        }
    }
}