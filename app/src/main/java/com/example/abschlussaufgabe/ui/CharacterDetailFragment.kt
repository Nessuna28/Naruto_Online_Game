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
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentCharacterDetailBinding


class CharacterDetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentCharacterDetailBinding

    private var id = 0


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageBackground.value?.let { viewModel.showImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.tvUserName.value?.let { viewModel.showTextView(it) }
        viewModel.imageSettings.value?.let { viewModel.showImages(it) }
        viewModel.imageProfile.value?.let { viewModel.showMaterialCard(it) }

        context?.let { viewModel.stopSound() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            id = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = viewModel.characters.value?.find { it.id == id }

        binding.tvCharacterName.text = character!!.name

        try {
            val imgUri = character.images[0].toUri().buildUpon().scheme("https").build()
            binding.ivCharacterImage.load(imgUri)
        } catch (e: Exception) {
            Log.e("CharacterDetail", "Error: ${e.message} im CharacterDetailFragment")
            binding.ivCharacterImage.setImageResource(R.drawable.no_picture)
        }

        //binding.tvClan.text = character.personal.clan
        binding.tvRank.text = character.rank.toString()
        binding.tvNatureTyp.text = character.natureType.toString()
        binding.tvJutsu.text = character.jutsu.toString()
        binding.tvUniqueTraits.text = character.uniqueTraits.toString()
        binding.tvTools.text = character.tools.toString()
        //binding.tvPersonal.text = character.personal.toString()
        binding.tvFamily.text = character.family.toString()



        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value?.setOnClickListener {
            findNavController().navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToHomeFragment())
        }

        viewModel.imageProfile.value?.setOnClickListener {
            findNavController().navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToProfileFragment())
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            findNavController().navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToProfileFragment())
        }
    }
}