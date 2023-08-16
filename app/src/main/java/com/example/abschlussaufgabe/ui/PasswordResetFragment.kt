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
import com.example.abschlussaufgabe.databinding.FragmentPasswordResetBinding

class PasswordResetFragment: Fragment() {

    private lateinit var binding: FragmentPasswordResetBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_reset, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRequestPassword.setOnClickListener {
            val email: String = binding.tietEmail.text.toString()
            if (email != "") {
                viewModel.sendPasswordReset(email)
            } else {
                viewModel.setMessage("Bitte gib deine Email ein!")
                viewModel.showToast(requireContext())
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}