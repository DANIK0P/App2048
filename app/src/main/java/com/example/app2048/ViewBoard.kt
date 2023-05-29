package com.example.app2048

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ViewBoard(
    private var boardLayout: GridLayout,
    private var scoreView: TextView,
    private var heightScoreView: TextView
) : AppCompatActivity() {

    private val textViewBlocks = mutableListOf(mutableListOf<TextView>())
    private var heightScore = 0
    private var size = 4
    var board = Board()


    @SuppressLint("ResourceAsColor")
    fun newGame() {
        board.board = MutableList(size) { MutableList(size) { 0 } }
        textViewBlocks.clear()
        boardLayout.removeAllViews()
        board.score = 0
        for (i in 0 until size) {
            textViewBlocks.add(mutableListOf())
            for (j in 0 until size) {
                val textView = TextView(boardLayout.context)
                textView.text = ""
                textView.gravity = Gravity.CENTER
                textView.textSize = 24f
                val layoutParams = GridLayout.LayoutParams().apply {
                    columnSpec = GridLayout.spec(j, GridLayout.FILL, 1f)
                    rowSpec = GridLayout.spec(i, GridLayout.FILL, 1f)
                    setMargins(8, 8, 8, 8)
                }
                boardLayout.addView(textView, layoutParams)
                textViewBlocks[i].add(textView)
            }
        }
        board.addRandomBlock()
        board.addRandomBlock()
        updateBoardView()
    }


    fun updateBoardView() {
        if (heightScore < board.score) heightScore = board.score
        for (i in 0 until size) {
            for (j in 0 until size) {
                textViewBlocks[i][j].text =
                    if (board.board[i][j] == 0) "" else board.board[i][j].toString()
                viewBlocks(board.board[i][j], textViewBlocks[i][j])
            }
        }
        scoreView.text = board.score.toString()
        heightScoreView.text = heightScore.toString()
    }


    @SuppressLint("ResourceAsColor")
    private fun viewBlocks(numBlock: Int, view: TextView) {
        if (numBlock == 0) view.text = ""
        else view.text = numBlock.toString()
        when (numBlock) {
            0 -> {
                view.setBackgroundColor(R.color.middle_bg)
                view.setTextColor(Color.BLACK)
            }

            2 -> {
                view.setBackgroundColor(Color.rgb(240, 240, 240))
                view.setTextColor(Color.BLACK)
            }

            4 -> {
                view.setBackgroundColor(Color.rgb(255, 255, 224))
                view.setTextColor(Color.BLACK)
            }

            8 -> {
                view.setBackgroundColor(Color.rgb(255, 200, 100))
                view.setTextColor(Color.WHITE)
            }

            16 -> {
                view.setBackgroundColor(Color.rgb(255, 140, 30))
                view.setTextColor(Color.WHITE)
            }

            32 -> {
                view.setBackgroundColor(Color.rgb(255, 100, 65))
                view.setTextColor(Color.WHITE)
            }

            64 -> {
                view.setBackgroundColor(Color.rgb(250, 80, 100))
                view.setTextColor(Color.WHITE)
            }

            128 -> {
                view.setBackgroundColor(Color.rgb(255, 220, 0))
                view.setTextColor(Color.WHITE)
            }

            256 -> {
                view.setBackgroundColor(Color.rgb(240, 240, 0))
                view.setTextColor(Color.BLACK)
            }

            512 -> {
                view.setBackgroundColor(Color.rgb(245, 245, 0))
                view.setTextColor(Color.BLACK)
            }

            1024 -> {
                view.setBackgroundColor(Color.rgb(250, 250, 0))
                view.setTextColor(Color.BLACK)
            }

            2048 -> {
                view.setBackgroundColor(Color.rgb(255, 255, 0))
                view.setTextColor(Color.BLACK)
            }

            else -> {
                view.setBackgroundColor(Color.BLACK)
                view.setTextColor(Color.WHITE)
            }
        }
    }
}