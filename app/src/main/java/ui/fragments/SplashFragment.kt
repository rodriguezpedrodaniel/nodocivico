package com.rodriguez.nodocivico.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rodriguez.nodocivico.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var logo: ImageView
    private lateinit var txtTitulo: TextView
    private lateinit var txtSubtitulo: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logo         = view.findViewById(R.id.imgLogo)
        txtTitulo    = view.findViewById(R.id.txtTitulo)
        txtSubtitulo = view.findViewById(R.id.txtSubtitulo)

        animarLogo()
        animarTextos()


        viewLifecycleOwner.lifecycleScope.launch {
            delay(2500)
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

    private fun animarLogo() {
        val scaleX = ObjectAnimator.ofFloat(logo, View.SCALE_X, 0.5f, 1f)
        val scaleY = ObjectAnimator.ofFloat(logo, View.SCALE_Y, 0.5f, 1f)
        val alpha  = ObjectAnimator.ofFloat(logo, View.ALPHA, 0f, 1f)

        listOf(scaleX, scaleY, alpha).forEach {
            it.duration     = 1200
            it.interpolator = AccelerateDecelerateInterpolator()
            it.start()
        }
    }

    private fun animarTextos() {
        txtTitulo.alpha    = 0f
        txtSubtitulo.alpha = 0f

        txtTitulo.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(1000)
            .start()

        txtSubtitulo.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(400)
            .setDuration(1000)
            .start()
    }
}