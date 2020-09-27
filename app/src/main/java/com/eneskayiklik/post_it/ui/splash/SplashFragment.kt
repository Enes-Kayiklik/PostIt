package com.eneskayiklik.post_it.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eneskayiklik.post_it.R

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var countDownTimer: CountDownTimer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCountDownTimer()
    }

    private fun setupCountDownTimer() {
        countDownTimer = object : CountDownTimer(100L, 100L) {
            override fun onFinish() {
                findNavController().navigate(R.id.action_splashFragment_to_notesFragment)
            }

            override fun onTick(p0: Long) {

            }
        }
    }

    override fun onStart() {
        super.onStart()
        countDownTimer.start()
    }

    override fun onStop() {
        super.onStop()
        countDownTimer.cancel()
    }
}