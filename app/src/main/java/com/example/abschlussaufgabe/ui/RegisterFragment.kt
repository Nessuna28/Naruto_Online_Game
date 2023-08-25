package com.example.abschlussaufgabe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnRegister.setOnClickListener {
            val email: String = binding.tietEmail.text.toString()
            val password: String = binding.tietPassword.text.toString()
            val password2: String = binding.tietPasswordRepeat.text.toString()
            if (email != "" && password != "") {
                if (password == password2) {
                    authViewModel.register(email, password)
                } else {
                    authViewModel.setMessage("Die beiden Passwörter müssen identisch sein!")
                    authViewModel.showToast(requireContext())
                }
            } else {
                authViewModel.setMessage("Bitte gib deine Email und dein Passwort ein!")
                authViewModel.showToast(requireContext())
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        authViewModel.registerSuccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
                }
        }
    }
}