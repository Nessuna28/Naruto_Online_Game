package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FightViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.StatisticAdapter
import com.example.abschlussaufgabe.databinding.FragmentStatisticsBinding
import com.example.abschlussaufgabe.helper.DataTouchHelper


class StatisticsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val fightViewModel: FightViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()

    private lateinit var binding: FragmentStatisticsBinding


    override fun onStart() {
        super.onStart()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StatisticAdapter(viewModel)
        binding.rvStatistic.adapter = adapter

        DataTouchHelper { position ->
            adapter.removeData(position)
        }.attachToRecyclerView(binding.rvStatistic)


        viewModel.dataList.observe(viewLifecycleOwner) {
            adapter.replaceDataSet(it)
            binding.tvVictorys.text = it.count { it.result == "Sieg" }.toString()
            binding.tvDefeats.text = it.count { it.result == "Niederlage" }.toString()
        }


        binding.ivDelete?.setOnClickListener {
            viewModel.deleteAllDataGame()
        }


        // Navigation

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value?.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(StatisticsFragmentDirections.actionStatisticsFragmentToHomeFragment())
        }

        viewModel.cvImageProfile.value!!.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(StatisticsFragmentDirections.actionStatisticsFragmentToProfileFragment())
        }

        viewModel.imageSettings.value!!.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(StatisticsFragmentDirections.actionStatisticsFragmentToProfileFragment())
        }
    }
}