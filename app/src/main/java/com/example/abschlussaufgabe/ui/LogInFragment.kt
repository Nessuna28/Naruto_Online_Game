package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.databinding.FragmentLogInBinding


class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()
    private val viewModel: MainViewModel by activityViewModels()



    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false)
        authViewModel.reset()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button
        binding.btnLogIn.setOnClickListener {
            val email: String = binding.tietEmail.text.toString()
            val password: String = binding.tietPassword.text.toString()
            if (email != "" && password != "") {
                authViewModel.login(email, password)
            } else {
                authViewModel.setMessage("Bitte gib deine Email und dein Passwort ein!")
                authViewModel.showToast(requireContext())
            }
        }

        // Navigation

        authViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                storeViewModel.currentProfile.observe(viewLifecycleOwner) { profile ->
                    if (profile != null) {
                        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
                    } else {
                        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToSplashScreenFragment())
                    }
                }
            }
        }

        binding.btnContinueAsGuest.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToRegisterFragment())
        }

        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToPasswordResetFragment())
        }
    }
}