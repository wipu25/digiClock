package com.example.digiclock.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.digiclock.arch.MainActivityViewModel
import com.example.digiclock.databinding.FragmentClockBinding

class DigitalClockFragment : Fragment() {

    private var _binding: FragmentClockBinding? = null
    private val viewModel: MainActivityViewModel by activityViewModels()

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.digitSecondDigitalClockDisplayManager.digitDisplayLiveData.observe(
            viewLifecycleOwner
        ) { map ->
            viewModel.setupLayoutWithNewDigit(binding.secondDigit, map)
        }

        viewModel.tenthSecondDigitalClockDisplayManager.digitDisplayLiveData.observe(
            viewLifecycleOwner
        ) { map ->
            viewModel.setupLayoutWithNewDigit(binding.secondTenth, map)
        }

        viewModel.digitMinuteDigitalClockDisplayManager.digitDisplayLiveData.observe(
            viewLifecycleOwner
        ) { map ->
            viewModel.setupLayoutWithNewDigit(binding.minuteDigit, map)
        }

        viewModel.tenthMinuteDigitalClockDisplayManager.digitDisplayLiveData.observe(
            viewLifecycleOwner
        ) { map ->
            viewModel.setupLayoutWithNewDigit(binding.minuteTenth, map)
        }

        viewModel.tenthHoursDigitalClockDisplayManager.digitDisplayLiveData.observe(
            viewLifecycleOwner
        ) { map ->
            viewModel.setupLayoutWithNewDigit(binding.hourTenth, map)
        }

        viewModel.digitHoursDigitalClockDisplayManager.digitDisplayLiveData.observe(
            viewLifecycleOwner
        ) { map ->
            viewModel.setupLayoutWithNewDigit(binding.hourDigit, map)
        }

        viewModel.getSystemClock()
    }
}