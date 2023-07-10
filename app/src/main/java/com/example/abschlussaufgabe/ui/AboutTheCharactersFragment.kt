package com.example.abschlussaufgabe.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.abschlussaufgabe.DataBinderMapperImpl
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.CharacterAdapter
import com.example.abschlussaufgabe.databinding.FragmentAboutTheCharactersBinding


class AboutTheCharactersFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentAboutTheCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutTheCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterListAdapter = CharacterAdapter(viewModel.characters)

        binding.rvAboutTheCharacters.adapter = characterListAdapter


    }

}