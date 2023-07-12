package com.example.abschlussaufgabe

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.abschlussaufgabe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.ivHome.setOnClickListener {
            TODO()
            binding.ivHome.visibility = View.GONE
        }

        binding.ivSettings.setOnClickListener {
            TODO()
        }

        binding.mcProfile.setOnClickListener {
            TODO()
        }
    }
}