package com.example.abschlussaufgabe.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.LocationAdapter
import com.example.abschlussaufgabe.databinding.FragmentLocationSelectionBinding


class LocationSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentLocationSelectionBinding


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageTitle.value?.let { viewModel.hideImages(it) }
        binding.btnFurther?.visibility = View.INVISIBLE
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
        }

        viewModel.location.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvLocationName?.text = it.name
            }
            if (it != null) {
                binding.ivLocatinImage?.setImageResource(it.image)
            }
        }

        binding.btnOk?.setOnClickListener {
            binding.btnOk!!.setBackgroundColor(Color.GREEN)
            binding.btnOk!!.setTextColor(Color.WHITE)
            viewModel.confirmSelectionLocation(true)
            binding.rvLocation?.isEnabled = false
            binding.btnFurther?.setBackgroundColor(Color.rgb(255, 105, 0))
            binding.btnFurther?.visibility = View.VISIBLE
        }

        binding.btnRandom?.setOnClickListener {
            viewModel.randomLocation()
        }

        binding.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnFurther?.setOnClickListener {
            if (viewModel.selectionConfirmLocation.value == true) {
                findNavController().navigate(LocationSelectionFragmentDirections.actionLocationSelectionFragmentToFightFragment())
            } // TODO: Toast schreiben
        }

        viewModel.imageHome.value?.setOnClickListener {
            findNavController().navigate(LocationSelectionFragmentDirections.actionLocationSelectionFragmentToHomeFragment())
        }
    }
}