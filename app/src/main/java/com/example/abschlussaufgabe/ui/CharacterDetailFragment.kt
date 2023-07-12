package com.example.abschlussaufgabe.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentCharacterDetailBinding


class CharacterDetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentCharacterDetailBinding

    private var name = ""
    private var image = ""
    private var clan = ""
    private var rank = ""
    private var natureTyp = ""
    private var jutsus = ""
    private var uniqueTraits = ""
    private var tools = ""
    private var personal = ""
    private var family = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            name = it.getString("name").toString()
            image = it.getString("images").toString()
            natureTyp = it.getString("natureType").toString()
            jutsus = it.getString("jutsus").toString()
            tools = it.getString("tools").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characters.observe(viewLifecycleOwner) {

            if (it != null) {
                binding.tvCharacterName.setText(name)
                binding.ivCharacterImage.setImageResource(image.toInt())
                binding.tvNatureTyp.setText(natureTyp)
                binding.tvJutsu.setText(jutsus)
                binding.tvTools.setText(tools)
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}