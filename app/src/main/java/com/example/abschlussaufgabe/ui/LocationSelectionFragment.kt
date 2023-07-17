package com.example.abschlussaufgabe.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.LocationAdapter
import com.example.abschlussaufgabe.adapter.SelectionCharacterEnemyAdapter
import com.example.abschlussaufgabe.adapter.SelectionCharacterPlayerAdapter
import com.example.abschlussaufgabe.data.datamodels.modelForFight.dataLists.LocationList
import com.example.abschlussaufgabe.databinding.FragmentCharacterSelectionBinding
import com.example.abschlussaufgabe.databinding.FragmentLocationSelectionBinding


class LocationSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentLocationSelectionBinding


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }

        val firstLocation = viewModel.locationList.value!!
        binding.tvLocationName?.text = firstLocation.keys.first()
        binding.ivLocatinImage?.setImageResource(firstLocation.values.first())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_location_selection, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.locationList.observe(viewLifecycleOwner) {
            binding.rvLocation?.adapter = LocationAdapter(it, viewModel)
            binding.tvLocationName?.text = it.keys.toString()
            // binding.ivLocatinImage?.setImageResource(it.values) TODO: Type mismatch
        }

        binding.btnOk?.setOnClickListener {
            binding.btnOk!!.setBackgroundColor(R.color.green)
            binding.btnOk!!.setTextColor(R.color.white)
            binding.rvLocation?.isClickable = false
            binding.btnFurther?.isClickable = true
        }

        binding.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnFurther?.setOnClickListener {
            findNavController().navigate(LocationSelectionFragmentDirections.actionLocationSelectionFragmentToFightFragment())
        }

        viewModel.imageHome.value?.setOnClickListener {
            findNavController().navigate(LocationSelectionFragmentDirections.actionLocationSelectionFragmentToHomeFragment())
        }
    }
}