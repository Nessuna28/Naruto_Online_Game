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
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.TeamAdapter
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.databinding.FragmentKniffelBinding
import com.example.abschlussaufgabe.databinding.PopupLayoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class KniffelFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val kniffelViewModel: KniffelViewModel by activityViewModels()

    private lateinit var binding: FragmentKniffelBinding

    private lateinit var selectedDice: Dice

    // Variablen für den Check ob die Würfel geklickt sind

    private var dice1IsClicked = false
    private var dice2IsClicked = false
    private var dice3IsClicked = false
    private var dice4IsClicked = false
    private var dice5IsClicked = false

    // Variablen für den Check ob die TextViews der Werte geklickt sind

    private var oneIsClicked = false
    private var twoIsClicked = false
    private var threeIsClicked = false
    private var fourIsClicked = false
    private var fiveIsClicked = false
    private var sixIsClicked = false
    private var threesomeIsClicked = false
    private var foursomeIsClicked = false
    private var fullHouseIsClicked = false
    private var bigStreetIsClicked = false
    private var littleStreetIsClicked = false
    private var kniffelIsClicked = false
    private var chanceIsClicked = false

    // Variablen für die Farbe

    private val primary = Color.rgb(255, 105, 0)
    private val green = Color.GREEN
    private val black = Color.BLACK
    private val gray = Color.GRAY
    private val white = Color.WHITE


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // öffnen des Popup-Fragments
        val popupDialog = Popup()
        popupDialog.show(parentFragmentManager, "Popup")

        // abhören der Teamauswahl im ViewModel
        kniffelViewModel.selectedDice.observe(viewLifecycleOwner) { selectedTeam ->
            // Überprüft, ob ein Team ausgewählt wurde um das Popup zu schließen
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
                binding.btnRollTheDice.setBackgroundColor(gray)
            }
        }

        kniffelViewModel.selectedDice.observe(viewLifecycleOwner) {
            if (kniffelViewModel.selectedDice.value != null) {
                selectedDice = kniffelViewModel.selectedDice.value!!
            }
            binding.ivDice1.setImageResource(it!!.diceSideList[0].image)
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

                kniffelViewModel.gameOver()
            }
        }

        kniffelViewModel.diceSideRolledDice1.observe(viewLifecycleOwner) {
            binding.ivRolledDice1.setImageResource(it!!.image)
        }

        kniffelViewModel.diceSideRolledDice2.observe(viewLifecycleOwner) {
            binding.ivRolledDice2.setImageResource(it!!.image)
        }

        kniffelViewModel.diceSideRolledDice3.observe(viewLifecycleOwner) {
            binding.ivRolledDice3.setImageResource(it!!.image)
        }

        kniffelViewModel.diceSideRolledDice4.observe(viewLifecycleOwner) {
            binding.ivRolledDice4.setImageResource(it!!.image)
        }

        kniffelViewModel.diceSideRolledDice5.observe(viewLifecycleOwner) {
            binding.ivRolledDice5.setImageResource(it!!.image)
        }


        // Buttons

        binding.btnRollTheDice.setOnClickListener {
            if (kniffelViewModel.attempts.value != 0) {
                kniffelViewModel.calculateAttempts()
                kniffelViewModel.initRollingDice()
                kniffelViewModel.rollTheDice()
                kniffelViewModel.setValues()
                resetCheckIsClickedDice()
            }
        }

        binding.btnOk.setOnClickListener {
            binding.btnRollTheDice.isEnabled = true
            kniffelViewModel.setAttempts(3)
            binding.btnRollTheDice.setBackgroundColor(primary)
            kniffelViewModel.setRolledDiceOfFalse()
            kniffelViewModel.resetValues()
            kniffelViewModel.initValues()
            kniffelViewModel.calculatePoints()
            resetColorDice()
        }

        // OnClickListener der Würfel

        binding.mcRolledDice1.setOnClickListener {
            dice1IsClicked = if (!dice1IsClicked) {
                kniffelViewModel.keepDice1(true)
                setColorDice(it, green)
                true
            } else {
                kniffelViewModel.keepDice1(false)
                setColorDice(it, primary)
                false
            }
        }

        binding.mcRolledDice2.setOnClickListener {
            dice2IsClicked = if (!dice2IsClicked) {
                kniffelViewModel.keepDice2(true)
                setColorDice(it, green)
                true
            } else {
                kniffelViewModel.keepDice2(false)
                setColorDice(it, primary)
                false
            }
        }

        binding.mcRolledDice3.setOnClickListener {
            dice3IsClicked = if (!dice3IsClicked) {
                kniffelViewModel.keepDice3(true)
                setColorDice(it, green)
                true
            } else {
                kniffelViewModel.keepDice3(false)
                setColorDice(it, primary)
                false
            }
        }

        binding.mcRolledDice4.setOnClickListener {
            dice4IsClicked = if (!dice4IsClicked) {
                kniffelViewModel.keepDice4(true)
                setColorDice(it, green)
                true
            } else {
                kniffelViewModel.keepDice4(false)
                setColorDice(it, primary)
                false
            }
        }

        binding.mcRolledDice5.setOnClickListener {
            dice5IsClicked = if (!dice5IsClicked) {
                kniffelViewModel.keepDice5(true)
                setColorDice(it, green)
                true
            } else {
                kniffelViewModel.keepDice5(false)
                setColorDice(it, primary)
                false
            }
        }


        // OnClickListener für die TextViews der Werte

        binding.tv1erValue.setOnClickListener {
            if (!oneIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                oneIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                oneIsClicked = false
            }
        }

        binding.tv2erValue.setOnClickListener {
            if (!twoIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                twoIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                twoIsClicked = false
            }
        }

        binding.tv3erValue.setOnClickListener {
            if (!threeIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                threeIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                threeIsClicked = false
            }
        }

        binding.tv4erValue.setOnClickListener {
            if (!fourIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                fourIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                fourIsClicked = false
            }
        }

        binding.tv5erValue.setOnClickListener {
            if (!fiveIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                fiveIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                fiveIsClicked = false
            }
        }

        binding.tv6erValue.setOnClickListener {
            if (!sixIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                sixIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                sixIsClicked = false
            }
        }

        binding.tv3xValue.setOnClickListener {
            if (!threesomeIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                threesomeIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                threesomeIsClicked = false
            }
        }

        binding.tv4xValue.setOnClickListener {
            if (!foursomeIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                foursomeIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                foursomeIsClicked = false
            }
        }

        binding.tvFullHouseValue.setOnClickListener {
            if (!fullHouseIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                fullHouseIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                fullHouseIsClicked = false
            }
        }

        binding.tvBigStreetValue.setOnClickListener {
            if (!bigStreetIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                bigStreetIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                bigStreetIsClicked = false
            }
        }

        binding.tvLittleStreetValue.setOnClickListener {
            if (!littleStreetIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                littleStreetIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                littleStreetIsClicked = false
            }
        }

        binding.tvKniffelValue.setOnClickListener {
            if (!kniffelIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                kniffelIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                kniffelIsClicked = false
            }
        }

        binding.tvChanceValue.setOnClickListener {
            if (!chanceIsClicked) {
                onTextViewClicked(it, true)
                setTextColorValues(it as TextView, black)
                binding.btnOk.isEnabled = true
                binding.btnOk.setBackgroundColor(primary)
                binding.btnOk.setTextColor(white)
                chanceIsClicked = true
            } else {
                onTextViewClicked(it, false)
                setTextColorValues(it as TextView, gray)
                binding.btnOk.isEnabled = false
                binding.btnOk.setBackgroundColor(gray)
                binding.btnOk.setTextColor(primary)
                chanceIsClicked = false
            }
        }

        kniffelViewModel.isGameOver.observe(viewLifecycleOwner) {
            if (it) {
                kniffelViewModel.resetAllPoints()
                resetColorDice()
                resetCheckIsClickedDice()
                resetCheckIsClickedTextView()
                // Erstelle den MaterialAlertDialogBuilder
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setTitle("Das Spiel ist beendet")
                    .setMessage("""Du hast ${kniffelViewModel.points.value} Punkte erzielt.
                        |
                        |
                        |Möchtest du noch eine Runde spielen?""".trimMargin())
                    .setPositiveButton("Ja") { dialog, which ->
                        findNavController().navigate(R.id.kniffelFragment)
                    }
                    .setNegativeButton("Nein") { dialog, which ->
                        findNavController().navigate(R.id.homeFragment)
                    }
                    .show()
            }
        }



        // Navigation

        binding.ivBack.setOnClickListener {
            // Erstelle den MaterialAlertDialogBuilder
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("Spiel verlassen")
                .setMessage("""Möchtest du dieses Spiel wirklich beenden?""".trimMargin())
                .setPositiveButton("OK") { dialog, which ->
                    findNavController().navigateUp()
                }
                .setNegativeButton("Abbrechen") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    // Funktionen
    private fun resetRolledDice() {

        binding.ivRolledDice1.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice2.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice3.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice4.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice5.setImageResource(selectedDice.diceSideList[0].image)
    }

    private fun onTextViewClicked(textView: View, check: Boolean) {

        val value = kniffelViewModel.values.value!!
        when (textView) {
            binding.tv1erValue -> value.one.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tv2erValue -> value.two.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tv3erValue -> value.three.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tv4erValue -> value.four.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tv5erValue -> value.five.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tv6erValue -> value.six.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tv3xValue -> value.threesome.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tv4xValue -> value.foursome.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tvFullHouseValue -> value.fullHouse.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tvBigStreetValue -> value.bigStreet.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tvLittleStreetValue -> value.littleStreet.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tvKniffelValue -> value.kniffel.let { kniffelViewModel.setCheckTextViews(it, check) }
            binding.tvChanceValue -> value.chance.let { kniffelViewModel.setCheckTextViews(it, check) }
        }
    }

    private fun resetCheckIsClickedDice() {

        dice1IsClicked = false
        dice2IsClicked = false
        dice3IsClicked = false
        dice4IsClicked = false
        dice5IsClicked = false
    }

    private fun resetCheckIsClickedTextView() {

        oneIsClicked = false
        twoIsClicked = false
        threeIsClicked = false
        fourIsClicked = false
        fiveIsClicked = false
        sixIsClicked = false
        threesomeIsClicked = false
        foursomeIsClicked = false
        fullHouseIsClicked = false
        bigStreetIsClicked = false
        littleStreetIsClicked = false
        kniffelIsClicked = false
        chanceIsClicked = false
    }

    private fun setColorDice(dice: View, color: Int) {

        dice.background.setTint(color)
    }

    private fun resetColorDice() {

        binding.mcRolledDice1.background.setTint(primary)
        binding.mcRolledDice2.background.setTint(primary)
        binding.mcRolledDice3.background.setTint(primary)
        binding.mcRolledDice4.background.setTint(primary)
        binding.mcRolledDice5.background.setTint(primary)
    }

    private fun setValueColorOfGray() {

        binding.tv1erValue.setTextColor(gray)
        binding.tv2erValue.setTextColor(gray)
        binding.tv3erValue.setTextColor(gray)
        binding.tv4erValue.setTextColor(gray)
        binding.tv5erValue.setTextColor(gray)
        binding.tv6erValue.setTextColor(gray)
        binding.tv3xValue.setTextColor(gray)
        binding.tv4xValue.setTextColor(gray)
        binding.tvFullHouseValue.setTextColor(gray)
        binding.tvBigStreetValue.setTextColor(gray)
        binding.tvLittleStreetValue.setTextColor(gray)
        binding.tvKniffelValue.setTextColor(gray)
        binding.tvChanceValue.setTextColor(gray)
    }

    private fun setTextColorValues(textView: TextView, textColor: Int) {

        textView.setTextColor(textColor)
    }
}