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
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.KniffelViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.TeamAdapter
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.databinding.FragmentKniffelBinding
import com.example.abschlussaufgabe.databinding.PopupLayoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class KniffelFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val kniffelViewModel: KniffelViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var binding: FragmentKniffelBinding


    // Variable für die ausgewählten Würfelbilder
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

    // Variablen für den Check ob Ok-Button geklickt wurde
    private var okButtonClicked = false

    // Variable für die jetztige Runde
    private var currentRound = 1

    // Variable für eine Liste der ein Boolean übergeben wird wenn der Ok-Button geklickt wurde
    private var rounds = mutableListOf<Boolean>()

    // Variable für eine Liste die einen View und einen Boolean übergeben bekommt
    // zum checken ob ich den View noch ändern kann
    private var listOfPair = mutableListOf<Pair<View, Boolean>>()

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


        // Observer für das automatische Schließen des Popups
        kniffelViewModel.selected.observe(viewLifecycleOwner) {
            if (it) {
                popupDialog.dismiss()
            }
        }
    }

    // Klasse für Popup
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

        // Funktion die Funktionen beinhaltet die ausgeführt werden sobald das Popup geschlossen wird
        override fun onDestroyView() {
            super.onDestroyView()

            kniffelViewModel.setSongs()
        }
    }

    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageSettings.value?.let { viewModel.showImages(it) }
        viewModel.tvUserName.value?.let { viewModel.hideTextView(it) }
        viewModel.cvImageProfile.value?.let { viewModel.showMaterialCard(it) }

        setTextColorValuesAtTheBeginning()

        kniffelViewModel.songList.observe(viewLifecycleOwner) {
            kniffelViewModel.stopSound()
            if (it.isNotEmpty()) {
                kniffelViewModel.setSelected(true)
                kniffelViewModel.volume.observe(viewLifecycleOwner) {
                    context?.let { kniffelViewModel.playFirstSong(it) }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kniffel, container, false)

        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeat(13) {
            rounds.add(false)
        }

        if (authViewModel.currentUser.value != null) {
            storeViewModel.currentProfile.observe(viewLifecycleOwner) {
                binding.tvNamePlayer.text = storeViewModel.currentProfile.value!!.userName
            }
        } else {
            binding.tvNamePlayer.text = R.string.guest.toString()
        }

        kniffelViewModel.points.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvPoints.text = it.toString()
            }
        }

        kniffelViewModel.attempts.observe(viewLifecycleOwner) {
            binding.tvAttemptsValue.text = it.toString()
            if (it == 0) {
                binding.btnRollTheDice.isEnabled = false
                binding.btnRollTheDice.setBackgroundColor(gray)
            }
        }

        kniffelViewModel.selectedDice.observe(viewLifecycleOwner) {
            if (it != null) {
                selectedDice = it
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

        // wenn auf den Button würfeln geklickt wird, wird geguckt ob noch Versuche übrig sind und dann
        // wird ein Versuch abgezogen,
        // die Werte werden berechnet,
        // die Würfelbilder ausgewählt,
        // und dann gewürfelt,
        // dann werden die Werte gesetzt und
        // die Checks ob die Würfel ausgesucht wurden wieder auf false gesetzt
        binding.btnRollTheDice.setOnClickListener {
            if (kniffelViewModel.attempts.value != 0) {
                kniffelViewModel.calculateAttempts()
                kniffelViewModel.initRollingDice()
                kniffelViewModel.rollTheDice()
                kniffelViewModel.setValues()
                resetCheckIsClickedDice()
            }
        }

        // beim Klick auf OK werden mehrere Funktionen ausgeführt wie
        // die Versuche wieder auf 3 gesetzt,
        // die Werte werden wieder auf 0 gesetzt wenn sie nicht ausgewählt wurden,
        // die Punkte werden berechnet und
        // die Farben zurückgesetzt
        binding.btnOk.setOnClickListener {
            okButtonClicked = !okButtonClicked
            currentRound++
            binding.btnRollTheDice.isEnabled = true
            kniffelViewModel.setAttempts(3)
            binding.btnRollTheDice.setBackgroundColor(primary)
            kniffelViewModel.setRolledDiceOfFalse()
            kniffelViewModel.resetValues()
            kniffelViewModel.initValues()
            kniffelViewModel.calculatePoints()
            resetColorDice()
            resetRolledDice()
            it.setBackgroundColor(gray)
            it.isEnabled = false
        }

        // OnClickListener der Würfel
        // prüft ob der Würfel bereits angeklickt wurde, ist die Variable dice..IsClicked false dann ...
        // ruft die Funktion auf und übergibt ihm true (damit sage ich dass ich den Würfel behalten möchte und er sich nicht mehr ändern soll
        // setzt die Farbe des Würfels auf grün, damit ich weiß, dass ich ihn ausgewählt habe
        // setzt danach die Variabel dice..IsClicked auf true
        // ist die Variable auf true, was bedeutet der Würfel wurde bereits ausgewählt werden die Funktionen rückgängig gemacht

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
        // die oberen Variablen als Check werden hier geändert ob geklickt oder nicht

        binding.tv1erValue.setOnClickListener {
            oneIsClicked = actionForClickingValue(oneIsClicked, it)
        }

        binding.tv2erValue.setOnClickListener {
            twoIsClicked = actionForClickingValue(twoIsClicked, it)
        }

        binding.tv3erValue.setOnClickListener {
            threeIsClicked = actionForClickingValue(threeIsClicked, it)
        }

        binding.tv4erValue.setOnClickListener {
            fourIsClicked = actionForClickingValue(fourIsClicked, it)
        }

        binding.tv5erValue.setOnClickListener {
            fiveIsClicked = actionForClickingValue(fiveIsClicked, it)
        }

        binding.tv6erValue.setOnClickListener {
            sixIsClicked = actionForClickingValue(sixIsClicked, it)
        }

        binding.tv3xValue.setOnClickListener {
            threesomeIsClicked = actionForClickingValue(threesomeIsClicked, it)
        }

        binding.tv4xValue.setOnClickListener {
            foursomeIsClicked = actionForClickingValue(foursomeIsClicked, it)
        }

        binding.tvFullHouseValue.setOnClickListener {
            fullHouseIsClicked = actionForClickingValue(fullHouseIsClicked, it)
        }

        binding.tvBigStreetValue.setOnClickListener {
            bigStreetIsClicked = actionForClickingValue(bigStreetIsClicked, it)
        }

        binding.tvLittleStreetValue.setOnClickListener {
            littleStreetIsClicked = actionForClickingValue(littleStreetIsClicked, it)
        }

        binding.tvKniffelValue.setOnClickListener {
            kniffelIsClicked = actionForClickingValue(kniffelIsClicked, it)
        }

        binding.tvChanceValue.setOnClickListener {
            chanceIsClicked = actionForClickingValue(chanceIsClicked, it)
        }

        // bei Klick auf die TextView wird erst gefragt ob man das Spiel wirklich beenden möchte
        // und dann je nach Auswahl ein neues Spiel gestartet
        binding.tvNewGame.setOnClickListener {
            // Erstelle den MaterialAlertDialogBuilder
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("Neues Spiel starten")
                .setMessage("""Möchtest du dieses Spiel wirklich beenden?""".trimMargin())
                .setPositiveButton("Ja") { dialog, which ->
                    newGame()
                    dialog.dismiss()
                }
                .setNegativeButton("Nein") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

        // bei Klick auf die TextView wird das PopUp erneut aufgerufen um sich die Würfelbilder neu auszusuchen
        binding.tvReselectDice.setOnClickListener {
            kniffelViewModel.stopSound()
            kniffelViewModel.setSelected(false)
            popupOpenAgain()
        }


        // wenn das Spiel beendet ist wird ein Dialog geöffnet
        // und es werden mehrere Funktionen ausgeführt wie das Zurücksetzen der Farben, das Stoppen des Sounds ect.
        kniffelViewModel.isGameOver.observe(viewLifecycleOwner) {
            if (it) {
                val points = kniffelViewModel.points.value
                newGame()
                kniffelViewModel.stopSound()

                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setTitle("Das Spiel ist beendet")
                    .setMessage(
                        """Du hast $points Punkte erzielt.
                        |
                        |
                        |Möchtest du noch eine Runde spielen?""".trimMargin()
                    )
                    .setPositiveButton("Ja") { dialog, which ->
                        findNavController().navigate(R.id.kniffelFragment)
                    }
                    .setNegativeButton("Nein") { dialog, which ->
                        kniffelViewModel.setSongListToEmpty()
                        findNavController().navigate(R.id.homeFragment)
                    }
                    .show()
            }
        }


        // Navigation

        binding.ivBack.setOnClickListener {
            kniffelViewModel.stopSound()
            findNavController().navigateUp()
        }

        viewModel.cvImageProfile.value!!.setOnClickListener {
            kniffelViewModel.stopSound()
            findNavController().navigate(KniffelFragmentDirections.actionKniffelFragmentToProfileFragment())
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            kniffelViewModel.stopSound()
            findNavController().navigate(KniffelFragmentDirections.actionKniffelFragmentToProfileFragment())
        }

        viewModel.imageHome.value!!.setOnClickListener {
            kniffelViewModel.stopSound()
            findNavController().navigate(KniffelFragmentDirections.actionKniffelFragmentToHomeFragment())
        }

        viewModel.imageSettings.value!!.setOnClickListener {
            findNavController().navigate(KniffelFragmentDirections.actionKniffelFragmentToSettingsFragment())
        }
    }

    // Funktionen

    // Popup-Fenster für die Auswahl der Bilder auf den Würfeln
    private fun popupOpenAgain() {

        // öffnen des Popup-Fragments
        val popupDialog = Popup()
        popupDialog.show(parentFragmentManager, "Popup")


        // Observer für das automatische Schließen des Popups
        kniffelViewModel.selected.observe(viewLifecycleOwner) {
            if (it) {
                popupDialog.dismiss()
            }
        }
    }

    // setzt das gleiche Bild auf alle 5 Würfel
    private fun resetRolledDice() {

        binding.ivRolledDice1.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice2.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice3.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice4.setImageResource(selectedDice.diceSideList[0].image)
        binding.ivRolledDice5.setImageResource(selectedDice.diceSideList[0].image)
    }

    // übergibt ob die TextViews angeklickt wurden
    private fun onTextViewClicked(textView: View, check: Boolean) {

        val value = kniffelViewModel.values.value!!

        when (textView) {
            binding.tv1erValue -> kniffelViewModel.changeOneBoolean(Pair(value.one.first, check))
            binding.tv2erValue -> kniffelViewModel.changeTwoBoolean(Pair(value.two.first, check))
            binding.tv3erValue -> kniffelViewModel.changeThreeBoolean(Pair(value.three.first, check))
            binding.tv4erValue -> kniffelViewModel.changeFourBoolean(Pair(value.four.first, check))
            binding.tv5erValue -> kniffelViewModel.changeFiveBoolean(Pair(value.five.first, check))
            binding.tv6erValue -> kniffelViewModel.changeSixBoolean(Pair(value.six.first, check))
            binding.tv3xValue -> kniffelViewModel.changeThreesomeBoolean(Pair(value.threesome.first, check))
            binding.tv4xValue -> kniffelViewModel.changeFoursomeBoolean(Pair(value.foursome.first, check))
            binding.tvFullHouseValue -> kniffelViewModel.changeFullHouseBoolean(Pair(value.fullHouse.first, check))
            binding.tvBigStreetValue -> kniffelViewModel.changeBigStreetBoolean(Pair(value.bigStreet.first, check))
            binding.tvLittleStreetValue -> kniffelViewModel.changeLittleStreetBoolean(Pair(value.littleStreet.first, check))
            binding.tvKniffelValue -> kniffelViewModel.changeKniffelBoolean(Pair(value.kniffel.first, check))
            binding.tvChanceValue -> kniffelViewModel.changeChanceBoolean(Pair(value.chance.first, check))
        }
    }


    // wenn die Textview geklickt wird werden einzelne Funktionen ausgeführt
    // bei nochmaligen Klick werden diese Änderungen wieder rückgängig gemacht
    private fun actionForClickingValue(variable: Boolean, it: View): Boolean {

        var updateVariable = variable

        if (!updateVariable) {
            onTextViewClicked(it, true)
            setTextColorValues(it as TextView, black)
            binding.btnOk.isEnabled = true
            binding.btnOk.setBackgroundColor(primary)
            binding.btnOk.setTextColor(white)
            updateVariable = true
        } else {
            onTextViewClicked(it, false)
            setTextColorValues(it as TextView, white)
            binding.btnOk.isEnabled = false
            binding.btnOk.setBackgroundColor(gray)
            binding.btnOk.setTextColor(primary)
            updateVariable = false
        }
        return updateVariable
    }

    // setzt alle 5 Würfel wieder auf nicht angeklickt
    private fun resetCheckIsClickedDice() {

        dice1IsClicked = false
        dice2IsClicked = false
        dice3IsClicked = false
        dice4IsClicked = false
        dice5IsClicked = false
    }

    // setzt alle TextViews auf nicht angeklickt
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

        okButtonClicked = false
    }

    // setzt die Farbe bei dem übergebenen Würfel auf eine übergebene Farbe
    private fun setColorDice(dice: View, color: Int) {

        dice.background.setTint(color)
    }

    // Farben werden zurückgesetzt

    private fun resetColorDice() {

        binding.mcRolledDice1.background.setTint(primary)
        binding.mcRolledDice2.background.setTint(primary)
        binding.mcRolledDice3.background.setTint(primary)
        binding.mcRolledDice4.background.setTint(primary)
        binding.mcRolledDice5.background.setTint(primary)
    }

    // setzt die Textfarben auf weiß es sei denn die TextViews sind noch ausgewählt
    private fun setTextColorValuesAtTheBeginning() {

        val  values = kniffelViewModel.values.value!!

        if (values.one.second) {
            binding.tv1erValue.setTextColor(black)
        } else {
            binding.tv1erValue.setTextColor(white)
        }

        if (values.two.second) {
            binding.tv2erValue.setTextColor(black)
        } else {
            binding.tv2erValue.setTextColor(white)
        }

        if (values.three.second) {
            binding.tv3erValue.setTextColor(black)
        } else {
            binding.tv3erValue.setTextColor(white)
        }

        if (values.four.second) {
            binding.tv4erValue.setTextColor(black)
        } else {
            binding.tv4erValue.setTextColor(white)
        }

        if (values.five.second) {
            binding.tv5erValue.setTextColor(black)
        } else {
            binding.tv5erValue.setTextColor(white)
        }

        if (values.six.second) {
            binding.tv6erValue.setTextColor(black)
        } else {
            binding.tv6erValue.setTextColor(white)
        }

        if (values.threesome.second) {
            binding.tv3xValue.setTextColor(black)
        } else {
            binding.tv3xValue.setTextColor(white)
        }

        if (values.foursome.second) {
            binding.tv4xValue.setTextColor(black)
        } else {
            binding.tv4xValue.setTextColor(white)
        }

        if (values.fullHouse.second) {
            binding.tvFullHouseValue.setTextColor(black)
        } else {
            binding.tvFullHouseValue.setTextColor(white)
        }

        if (values.bigStreet.second) {
            binding.tvBigStreetValue.setTextColor(black)
        } else {
            binding.tvBigStreetValue.setTextColor(white)
        }

        if (values.littleStreet.second) {
            binding.tvLittleStreetValue.setTextColor(black)
        } else {
            binding.tvLittleStreetValue.setTextColor(white)
        }

        if (values.kniffel.second) {
            binding.tvKniffelValue.setTextColor(black)
        } else {
            binding.tvKniffelValue.setTextColor(white)
        }

        if (values.chance.second) {
            binding.tvChanceValue.setTextColor(black)
        } else {
            binding.tvChanceValue.setTextColor(white)
        }
    }

    // setzt den Text der übergeben TextView auf die übergeben Farbe
    private fun setTextColorValues(textView: TextView, textColor: Int) {

        textView.setTextColor(textColor)
    }

    // setzt die Farben und Werte zurück auf Anfang
    private fun newGame() {

        kniffelViewModel.resetAllPoints()
        resetColorDice()
        resetCheckIsClickedDice()
        resetCheckIsClickedTextView()
        kniffelViewModel.setAttempts(3)
        setTextColorValuesAtTheBeginning()
        resetRolledDice()
    }
}