package com.example.abschlussaufgabe.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    private val handler = Handler()

    override fun onStart() {
        super.onStart()

        binding.ivTitle.visibility = View.VISIBLE
        binding.tvSubtitle.visibility = View.VISIBLE
        binding.ivImageBeginn.visibility = View.VISIBLE
        binding.ivKonoha.visibility = View.INVISIBLE
        binding.ivNarutoOriginal.visibility = View.INVISIBLE
        binding.ivNaruto1.visibility = View.INVISIBLE
        binding.ivNaruto2.visibility = View.INVISIBLE
        binding.ivNaruto3.visibility = View.INVISIBLE
        binding.ivNaruto4.visibility = View.INVISIBLE
        binding.ivNaruto5.visibility = View.INVISIBLE
        binding.ivNaruto6.visibility = View.INVISIBLE
        binding.ivNaruto7.visibility = View.INVISIBLE
        binding.ivNaruto8.visibility = View.INVISIBLE
        binding.ivNaruto9.visibility = View.INVISIBLE
        binding.ivNaruto10.visibility = View.INVISIBLE
        binding.ivNaruto11.visibility = View.INVISIBLE
        binding.ivNaruto12.visibility = View.INVISIBLE
        binding.ivNaruto13.visibility = View.INVISIBLE
        binding.ivNaruto14.visibility = View.INVISIBLE
        binding.ivNaruto15.visibility = View.INVISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false)

        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment())
        }, 10000)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler.postDelayed({
            binding.ivImageBeginn.visibility = View.INVISIBLE
            binding.ivKonoha.visibility = View.VISIBLE
            binding.ivNarutoOriginal.visibility = View.VISIBLE

            handler.postDelayed({
                binding.ivNaruto1.visibility = View.VISIBLE
                binding.ivNaruto2.visibility = View.VISIBLE

                handler.postDelayed({
                    binding.ivNaruto3.visibility = View.VISIBLE
                    binding.ivNaruto4.visibility = View.VISIBLE
                    binding.ivNaruto5.visibility = View.VISIBLE

                    handler.postDelayed({
                        binding.ivNaruto6.visibility = View.VISIBLE
                        binding.ivNaruto7.visibility = View.VISIBLE

                        handler.postDelayed({
                            binding.ivNaruto8.visibility = View.VISIBLE
                            binding.ivNaruto9.visibility = View.VISIBLE
                            binding.ivNaruto10.visibility = View.VISIBLE

                            handler.postDelayed({
                                binding.ivNaruto11.visibility = View.VISIBLE
                                binding.ivNaruto12.visibility = View.VISIBLE

                                handler.postDelayed({
                                    binding.ivNaruto13.visibility = View.VISIBLE
                                    binding.ivNaruto14.visibility = View.VISIBLE
                                    binding.ivNaruto15.visibility = View.VISIBLE

                                    handler.postDelayed({
                                        binding.ivNarutoOriginal.setImageResource(R.drawable.naruto)

                                        handler.postDelayed({
                                            scale()

                                            handler.postDelayed({
                                                binding.ivNarutoOriginal.setImageResource(R.drawable.naruto_pose)

                                            }, 600)
                                        }, 600)
                                    }, 1000)
                                }, 600)
                            }, 600)
                        }, 600)
                    }, 600)
                }, 600)
            }, 1300)
        }, 2000)
    }

    private fun scale() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 3f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 3f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.ivNarutoOriginal, scaleX, scaleY)
        animator.duration = 1500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.interpolator = BounceInterpolator()
        animator.start()
    }
}