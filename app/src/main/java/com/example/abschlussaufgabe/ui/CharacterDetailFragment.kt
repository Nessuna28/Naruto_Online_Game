package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentCharacterDetailBinding

const val TAGDETAIL = "CharacterDetailFragment"


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


    override fun onStart() {
        super.onStart()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

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

                binding.tvCharacterName.setText(name)

                try {
                    val imgUri = image.toUri().buildUpon().scheme("https").build()
                    binding.ivCharacterImage.load(imgUri)
                } catch (e: Exception) {
                    Log.e(TAGDETAIL, "Error: ${e.message} im CharacterDetailFragment")
                    binding.ivCharacterImage.setImageResource(R.drawable.no_picture)
                }
                binding.tvNatureTyp.setText(natureTyp)
                binding.tvJutsu.setText(jutsus)
                binding.tvTools.setText(tools)


        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value?.setOnClickListener {
            findNavController().navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToHomeFragment())
        }
    }
}