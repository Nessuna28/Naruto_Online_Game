package com.example.abschlussaufgabe.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.CharacterForFightAdapter
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.FightDataForDatabase.Player
import com.example.abschlussaufgabe.databinding.FragmentCharacterSelectionBinding


class CharacterSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentCharacterSelectionBinding
    lateinit var dataPlayer: Player
    //private val characterCom = viewModel.characterLiveData.value?.random()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel.updateDatabase(dataPlayer)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_selection, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.rvCharactersPlayer.adapter
            binding.rvCharactersCom.adapter
            //binding.rvSelectionJutsusPlayer.adapter = CharacterForFightAdapter(it)
            //binding.rvSelectionJutsusCom.adapter = CharacterForFightAdapter(it)
    }
}