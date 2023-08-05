package com.example.abschlussaufgabe.ui


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.TeamAdapter
import com.example.abschlussaufgabe.databinding.FragmentKniffelBinding
import com.example.abschlussaufgabe.databinding.PopupLayoutBinding


class KniffelFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val kniffelViewModel: KniffelViewModel by activityViewModels()

    private lateinit var binding: FragmentKniffelBinding


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Öffnen des Popup-Fragments
        val popupDialog = Popup()
        popupDialog.show(parentFragmentManager, "Popup")

        // Abhören der Teamauswahl im ViewModel
        kniffelViewModel.selectionDice.observe(viewLifecycleOwner) { selectedTeam ->
            // Überprüfen, ob ein Team ausgewählt wurde, und das Popup zu schließen
            if (selectedTeam != null && popupDialog.isVisible) {
                popupDialog.dismiss()
            }
        }
    }

    class Popup : DialogFragment() {

        private lateinit var binding: PopupLayoutBinding
        private val kniffelViewModel: KniffelViewModel by activityViewModels()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = DataBindingUtil.inflate(inflater, R.layout.popup_layout, container, false)
            binding.rvTeam.adapter = TeamAdapter(kniffelViewModel.diceList.value!!) { team ->
                kniffelViewModel.selectTeam(team)
            }
            return binding.root
        }

        @SuppressLint("UseGetLayoutInflater")
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(requireActivity())
            val view = onCreateView(LayoutInflater.from(requireContext()), null, null)
            builder.setView(view)
            return builder.create()
        }
    }

    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.userName.value?.let { viewModel.hideTextView(it) }

        context?.let { viewModel.setSound(it, R.raw.song_theme) }

        kniffelViewModel.setAttempts(3)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kniffel, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kniffelViewModel.points.observe(viewLifecycleOwner) {
            binding.tvPoints.text = it.toString()
        }

        kniffelViewModel.attempts.observe(viewLifecycleOwner) {
            binding.tvAttemptsValue.text = it.toString()
            if (it == 0) {
                binding.btnRollTheDice.isEnabled = false
                binding.btnRollTheDice.setBackgroundColor(Color.GRAY)
            }
        }

        kniffelViewModel.selectionDice.observe(viewLifecycleOwner) {
            binding.ivDice1.setImageResource(it.image1)
            binding.ivDice2.setImageResource(it.image2)
            binding.ivDice3.setImageResource(it.image3)
            binding.ivDice4.setImageResource(it.image4)
            binding.ivDice5.setImageResource(it.image5)
            binding.ivDice6.setImageResource(it.image6)

            resetRolledDice()
        }


        // Button

        binding.btnRollTheDice.setOnClickListener {
            if (kniffelViewModel.attempts.value != 0) {
                kniffelViewModel.calculateAttempts()
                kniffelViewModel.rollTheDice()
                setRandomImages()
            }
        }

        binding.btnOk.setOnClickListener {
            binding.btnRollTheDice.isEnabled = true
            kniffelViewModel.setAttempts(3)
            binding.btnRollTheDice.setBackgroundColor(Color.rgb(255, 105, 0))

        }

        // Navigation

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun resetRolledDice() {

        binding.ivRolledDice1.setImageResource(kniffelViewModel.selectionDice.value!!.image1)
        binding.ivRolledDice2.setImageResource(kniffelViewModel.selectionDice.value!!.image1)
        binding.ivRolledDice3.setImageResource(kniffelViewModel.selectionDice.value!!.image1)
        binding.ivRolledDice4.setImageResource(kniffelViewModel.selectionDice.value!!.image1)
        binding.ivRolledDice5.setImageResource(kniffelViewModel.selectionDice.value!!.image1)
    }

    private fun setRandomImages() {

        binding.ivRolledDice1.setImageResource(kniffelViewModel.randomDice1.value!!)
        binding.ivRolledDice2.setImageResource(kniffelViewModel.randomDice2.value!!)
        binding.ivRolledDice3.setImageResource(kniffelViewModel.randomDice3.value!!)
        binding.ivRolledDice4.setImageResource(kniffelViewModel.randomDice4.value!!)
        binding.ivRolledDice5.setImageResource(kniffelViewModel.randomDice5.value!!)
    }

    private fun setValue() {

    }
}