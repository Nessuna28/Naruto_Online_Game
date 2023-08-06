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
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.databinding.FragmentKniffelBinding
import com.example.abschlussaufgabe.databinding.PopupLayoutBinding


class KniffelFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val kniffelViewModel: KniffelViewModel by activityViewModels()

    private lateinit var binding: FragmentKniffelBinding

    private lateinit var selectedDice: Dice


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Öffnen des Popup-Fragments
        val popupDialog = Popup()
        popupDialog.show(parentFragmentManager, "Popup")

        // Abhören der Teamauswahl im ViewModel
        kniffelViewModel.selectedDice.observe(viewLifecycleOwner) { selectedTeam ->
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

        setValueColorOfGray()
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

        kniffelViewModel.selectedDice.observe(viewLifecycleOwner) {
            selectedDice = kniffelViewModel.selectedDice.value!!
            binding.ivDice1.setImageResource(it.diceSide1.image)
            binding.ivDice2.setImageResource(it.diceSide2.image)
            binding.ivDice3.setImageResource(it.diceSide3.image)
            binding.ivDice4.setImageResource(it.diceSide4.image)
            binding.ivDice5.setImageResource(it.diceSide5.image)
            binding.ivDice6.setImageResource(it.diceSide6.image)

            resetRolledDice()
        }

        kniffelViewModel.one.observe(viewLifecycleOwner) {
            binding.tv1erValue.text = it.toString()
        }

        kniffelViewModel.two.observe(viewLifecycleOwner) {
            binding.tv2erValue.text = it.toString()
        }

        kniffelViewModel.three.observe(viewLifecycleOwner) {
            binding.tv3erValue.text = it.toString()
        }

        kniffelViewModel.four.observe(viewLifecycleOwner) {
            binding.tv4erValue.text = it.toString()
        }

        kniffelViewModel.five.observe(viewLifecycleOwner) {
            binding.tv5erValue.text = it.toString()
        }

        kniffelViewModel.six.observe(viewLifecycleOwner) {
            binding.tv6erValue.text = it.toString()
        }

        kniffelViewModel.threesome.observe(viewLifecycleOwner) {
            binding.tv3xValue.text = it.toString()
        }

        kniffelViewModel.foursome.observe(viewLifecycleOwner) {
            binding.tv4xValue.text = it.toString()
        }

        kniffelViewModel.fullHouse.observe(viewLifecycleOwner) {
            binding.tvFullHouseValue.text = it.toString()
        }

        kniffelViewModel.bigStreet.observe(viewLifecycleOwner) {
            binding.tvBigStreetValue.text = it.toString()
        }

        kniffelViewModel.littleStreet.observe(viewLifecycleOwner) {
            binding.tvLittleStreetValue.text = it.toString()
        }

        kniffelViewModel.kniffel.observe(viewLifecycleOwner) {
            binding.tvKniffelValue.text = it.toString()
        }

        kniffelViewModel.chance.observe(viewLifecycleOwner) {
            binding.tvChanceValue.text = it.toString()
        }


        // Button

        binding.btnRollTheDice.setOnClickListener {
            if (kniffelViewModel.attempts.value != 0) {
                kniffelViewModel.calculateAttempts()
                kniffelViewModel.rollTheDice()
                kniffelViewModel.setValueDice()
                setRandomImages()
            }
        }

        binding.mcRolledDice1.setOnClickListener {
            kniffelViewModel.diceToKeep.add(kniffelViewModel.randomDice1.value!!)
        }

        binding.mcRolledDice2.setOnClickListener {
            kniffelViewModel.diceToKeep.add(kniffelViewModel.randomDice2.value!!)
        }

        binding.mcRolledDice3.setOnClickListener {
            kniffelViewModel.diceToKeep.add(kniffelViewModel.randomDice3.value!!)
        }

        binding.mcRolledDice4.setOnClickListener {
            kniffelViewModel.diceToKeep.add(kniffelViewModel.randomDice4.value!!)
        }

        binding.mcRolledDice5.setOnClickListener {
            kniffelViewModel.diceToKeep.add(kniffelViewModel.randomDice5.value!!)
        }

        binding.btnOk.setOnClickListener {
            binding.btnRollTheDice.isEnabled = true
            kniffelViewModel.setAttempts(3)
            binding.btnRollTheDice.setBackgroundColor(Color.rgb(255, 105, 0))
            kniffelViewModel.calculatePoints()
        }

        // Navigation

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun resetRolledDice() {

        binding.ivRolledDice1.setImageResource(selectedDice.diceSide1.image)
        binding.ivRolledDice2.setImageResource(selectedDice.diceSide1.image)
        binding.ivRolledDice3.setImageResource(selectedDice.diceSide1.image)
        binding.ivRolledDice4.setImageResource(selectedDice.diceSide1.image)
        binding.ivRolledDice5.setImageResource(selectedDice.diceSide1.image)
    }

    private fun setRandomImages() {

            binding.ivRolledDice1.setImageResource(kniffelViewModel.randomDice1.value!!.image)
            binding.ivRolledDice2.setImageResource(kniffelViewModel.randomDice2.value!!.image)
            binding.ivRolledDice3.setImageResource(kniffelViewModel.randomDice3.value!!.image)
            binding.ivRolledDice4.setImageResource(kniffelViewModel.randomDice4.value!!.image)
            binding.ivRolledDice5.setImageResource(kniffelViewModel.randomDice5.value!!.image)
    }

    private fun setValueColorOfGray() {

        binding.tv1erValue.setTextColor(Color.GRAY)
        binding.tv2erValue.setTextColor(Color.GRAY)
        binding.tv3erValue.setTextColor(Color.GRAY)
        binding.tv4erValue.setTextColor(Color.GRAY)
        binding.tv5erValue.setTextColor(Color.GRAY)
        binding.tv6erValue.setTextColor(Color.GRAY)
        binding.tv3xValue.setTextColor(Color.GRAY)
        binding.tv4xValue.setTextColor(Color.GRAY)
        binding.tvFullHouseValue.setTextColor(Color.GRAY)
        binding.tvBigStreetValue.setTextColor(Color.GRAY)
        binding.tvLittleStreetValue.setTextColor(Color.GRAY)
        binding.tvKniffelValue.setTextColor(Color.GRAY)
        binding.tvChanceValue.setTextColor(Color.GRAY)
    }
}