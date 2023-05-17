package com.example.app2048

import android.widget.TextView

class AnimationBlocks {

    fun animateBlock(tileView: TextView, startX: Int, startY: Int, endX: Int, endY: Int) {
        val blockWidth = tileView.width + 8
        val blockHeight = tileView.height + 8

        val translationX = (endX - startX) * blockWidth
        val translationY = (endY - startY) * blockHeight

        tileView.animate()
            .translationXBy(translationX.toFloat())
            .translationYBy(translationY.toFloat())
            .setDuration(500)
            .start()
    }


}