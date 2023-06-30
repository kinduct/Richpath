package com.richpathanimator.sample

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.richpath.RichPathView
import com.richpathanimator.AnimationListener
import com.richpathanimator.RichPathAnimator

class MainActivity : AppCompatActivity() {

    private var richPathAnimator: RichPathAnimator? = null

    private lateinit var icAndroidRichPathView: RichPathView
    private lateinit var animationSamplesButton: AppCompatButton
    private lateinit var compoundViewSamplesButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        icAndroidRichPathView.setVectorDrawable(R.drawable.color_picker)

        icAndroidRichPathView = findViewById(R.id.icAndroidRichPathView)
        animationSamplesButton = findViewById(R.id.animationSamplesButton)
        compoundViewSamplesButton = findViewById(R.id.compoundViewSamplesButton)

        icAndroidRichPathView.setOnClickListener { animateAndroid() }
        animationSamplesButton.setOnClickListener { openAnimationSamples() }
        compoundViewSamplesButton.setOnClickListener { openCompoundViewSamples() }
    }

    override fun onResume() {
        super.onResume()
        animateAndroid()
    }

    private fun animateAndroid() {

        val allPaths = icAndroidRichPathView.findAllRichPaths()
        val head = icAndroidRichPathView.findRichPathByName("head")!!
        val body = icAndroidRichPathView.findRichPathByName("body")!!
        val rHand = icAndroidRichPathView.findRichPathByName("r_hand")!!
        val lHand = icAndroidRichPathView.findRichPathByName("l_hand")!!

        richPathAnimator = RichPathAnimator.animate(*allPaths)
                .trimPathEnd(0f, 1f)
                .duration(800)
                .animationListener(object : AnimationListener {
                    override fun onStart() {
                        head.fillColor = Color.TRANSPARENT
                        body.fillColor = Color.TRANSPARENT
                        rHand.fillColor = Color.TRANSPARENT
                        lHand.fillColor = Color.TRANSPARENT
                        rHand.rotation = 0f
                    }

                    override fun onStop() {}
                })
                .thenAnimate(*allPaths)
                .fillColor(Color.TRANSPARENT, -0x5b39c7)
                .interpolator(AccelerateInterpolator())
                .duration(900)
                .thenAnimate(rHand)
                .rotation(-150f)
                .duration(700)
                .thenAnimate(rHand)
                .rotation(-150f, -130f, -150f, -130f, -150f, -130f, -150f)
                .duration(2000)
                .thenAnimate(rHand)
                .rotation(0f)
                .duration(500)
                .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        richPathAnimator?.cancel()
    }

    private fun openAnimationSamples() {
        startActivity(Intent(this, AnimationSamplesActivity::class.java))
    }

    private fun openCompoundViewSamples() {
        startActivity(Intent(this, CompoundViewSamplesActivity::class.java))
    }
}
