package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by activityViewModels()


    override fun onStart() {
        super.onStart()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.imageTitle.value?.let { viewModel.hideImages(it) }
        viewModel.imageHome.value?.let { viewModel.hideImages(it) }

        context?.let { viewModel.stopSound() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCharacterTitle.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAboutTheCharactersFragment())
        }

        binding.tvStatisticTitle.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStatisticsFragment())
        }

        binding.tvFightTitle.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCharacterSelectionFragment())
        }
    }
}
