package es.icp.pruebas_commons

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat.postDelayed
import es.icp.icp_commons.viewAnimations.*
import es.icp.pruebas_commons.databinding.ActivityAnimationExamplesBinding

class AnimationExamplesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationExamplesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationExamplesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            rotateYForever.rotateYForever()
            rotateXForever.rotateXForever()
            probarVisibleSmoothly.setOnClickListener { visibleSmoothly.visibleSmoothly() }
            probarGoneSmoothly.setOnClickListener { goneSmoothly.goneSmoothly() }
            shakeX.shakeAnimationXForever()
            shakeY.shakeAnimationYForever()

        }

    }

}