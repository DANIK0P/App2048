package com.example.app2048

import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView

class AnimationBlocks {

    fun animateBlock(tileView: TextView, startX: Int, startY: Int, endX: Int, endY: Int) {

        val translationY = (endX - startX) * 91
        val translationX = (endY - startY) * 91

        tileView.animate()
            .translationXBy(translationX.toFloat())
            .translationYBy(translationY.toFloat())
            .setDuration(500)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

}