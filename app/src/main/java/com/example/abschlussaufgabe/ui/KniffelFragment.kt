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

    private var dice1IsClick = false
    private var dice2IsClick = false
    private var dice3IsClick = false
    private var dice4IsClick = false
    private var dice5IsClick = false


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
        ): View {
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

    @SuppressLint("ResourceType")
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
            binding.ivDice1.setImageResource(it.diceSideList[0].image)
            binding.ivDice2.setImageResource(it.diceSideList[1].image)
            binding.ivDice3.setImageResource(it.diceSideList[2].image)
            binding.ivDice4.setImageResource(it.diceSideList[3].image)
            binding.ivDice5.setImageResource(it.diceSideList[4].image)
            binding.ivDice6.setImageResource(it.diceSideList[5].image)

            resetRolledDice()
        }

        kniffelViewModel.values.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tv1erValue.text = it.one.first.toString()
                binding.tv2erValue.text = it.two.first.toString()
                binding.tv3erValue.text = it.three.first.toString()
                binding.tv4erValue.text = it.four.first.toString()
                binding.tv5erValue.text = it.five.first.toString()
                binding.tv6erValue.text = it.six.first.toString()
                binding.tvBonusValue.text = it.bonus.first.toString()
                binding.tv3xValue.text = it.threesome.first.toString()
                binding.tv4xValue.text = it.foursome.first.toString()
                binding.tvFullHouseValue.text = it.fullHouse.first.toString()
                binding.tvBigStreetValue.text = it.bigStreet.first.toString()
                binding.tvLittleStreetValue.text = it.littleStreet.first.toString()
                binding.tvKniffelValue.text = it.kniffel.first.toString()
                binding.tvChanceValue.text = it.chance.first.toString()
            }
        }


        // Button

        binding.btnRollTheDice.setOnClickListener {
            if (kniffelViewModel.attempts.value != 0) {
                kniffelViewModel.calculateAttempts()
                kniffelViewModel.initRollingDice()
                kniffelViewModel.rollTheDice()
                kniffelViewModel.initValues()
                kniffelViewModel.setValueDice()
            }
        }

        binding.mcRolledDice1.setOnClickListener {
            if (!dice1IsClick) {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice1.value!!, true)
                //binding.mcRolledDice1.setCardBackgroundColor(Color.GREEN)
                dice1IsClick = true
            } else {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice1.value!!, false)
                dice1IsClick = false
            }
        }

        binding.mcRolledDice2.setOnClickListener {
            if (!dice2IsClick) {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice2.value!!, true)
                //binding.mcRolledDice1.setCardBackgroundColor(Color.GREEN)
                dice2IsClick = true
            } else {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice2.value!!, false)
                dice2IsClick = false
            }
        }

        binding.mcRolledDice3.setOnClickListener {
            if (!dice3IsClick) {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice3.value!!, true)
                //binding.mcRolledDice1.setCardBackgroundColor(Color.GREEN)
                dice3IsClick = true
            } else {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice3.value!!, false)
                dice3IsClick = false
            }
        }

        binding.mcRolledDice4.setOnClickListener {
            if (!dice4IsClick) {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice4.value!!, true)
                //binding.mcRolledDice1.setCardBackgroundColor(Color.GREEN)
                dice4IsClick = true
            } else {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice4.value!!, false)
                dice4IsClick = false
            }
        }

        binding.mcRolledDice5.setOnClickListener {
            if (!dice5IsClick) {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice5.value!!, true)
                //binding.mcRolledDice1.setCardBackgroundColor(Color.GREEN)
                dice5IsClick = true
            } else {
                kniffelViewModel.diceToKeep(kniffelViewModel.rolledDice5.value!!, false)
                dice5IsClick = false
            }
        }

        binding.btnOk.setOnClickListener {
            binding.btnRollTheDice.isEnabled = true
            kniffelViewModel.setAttempts(3)
            binding.btnRollTheDice.setBackgroundColor(Color.rgb(255, 105, 0))
            kniffelViewModel.setRolledDiceOfFalse()
            kniffelViewModel.resetValues()
            kniffelViewModel.initValues()
            kniffelViewModel.calculatePoints()
        }

        binding.tv1erValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tv1erValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tv2erValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tv2erValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tv3erValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tv3erValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tv4erValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tv4erValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tv5erValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tv5erValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tv6erValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tv6erValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tv3xValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tv3xValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tv4xValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tv4xValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tvFullHouseValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tvFullHouseValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tvBigStreetValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tvBigStreetValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tvLittleStreetValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tvLittleStreetValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tvKniffelValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tvKniffelValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        binding.tvChanceValue.setOnClickListener {
            onTextViewClicked(it)
            binding.tvChanceValue.setTextColor(Color.BLACK)
            binding.btnOk.isEnabled = true
        }

        // Navigation

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun resetRolledDice() {

        binding.ivRolledDice1.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice2.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice3.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice4.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice5.setImageResource(selectedDice.diceSideList[0].image)
    }

    private fun setRandomImages() {


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

    private fun onTextViewClicked(textView: View) {

        val value = kniffelViewModel.values.value!!
        when (textView) {
            binding.tv1erValue -> kniffelViewModel.setCheckTextViews(value.one)
            binding.tv2erValue -> value.two.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tv3erValue -> value.three.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tv4erValue -> value.four.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tv5erValue -> value.five.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tv6erValue -> value.six.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tv3xValue -> value.threesome.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tv4xValue -> value.foursome.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tvFullHouseValue -> value.fullHouse.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tvBigStreetValue -> value.bigStreet.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tvLittleStreetValue -> value.littleStreet.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tvKniffelValue -> value.kniffel.let { kniffelViewModel.setCheckTextViews(it) }
            binding.tvChanceValue -> value.chance.let { kniffelViewModel.setCheckTextViews(it) }
        }
    }
}