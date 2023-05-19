package com.example.app2048

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class Board(
    private var boardLayout: GridLayout,
    private var scoreView: TextView,
    private var heightScoreView: TextView
) : AppCompatActivity() {


    private val textViewBlocks = mutableListOf(mutableListOf<TextView>())
    var board = mutableListOf(mutableListOf<Int>())
    private var size = 4
    private var score = 0
    private var heightScore = 0
    private val stXY = mutableListOf<Pair<Int, Int>>()
    private val eXY = mutableListOf<Pair<Int, Int>>()


    fun addRandomBlock() {
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until this.size) {
            for (j in 0 until this.size) {
                if (board[i][j] == 0) {
                    emptyCells.add(Pair(i, j))
                }
            }
        }
        if (emptyCells.isNotEmpty()) {
            val randomCell = emptyCells.random()
            board[randomCell.first][randomCell.second] = if (Random.nextDouble() > 0.1) 2 else 4
        }
    }


    fun notAddBlock(): Boolean {
        if (stXY != eXY) return true
        return false
    }


    @SuppressLint("ResourceAsColor")
    fun newGame() {
        board = MutableList(size) { MutableList(size) { 0 } }
        textViewBlocks.clear()
        boardLayout.removeAllViews()
        score = 0
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
        addRandomBlock()
        addRandomBlock()
        updateBoardView()
    }


    fun isGameOver(): Boolean {
        for (i in 0 until this.size) {
            for (j in 0 until this.size) {
                if (board[i][j] == 0) {
                    return false
                }
                if (i < 3 && board[i][j] == board[i + 1][j]) {
                    return false
                }
                if (j < 3 && board[i][j] == board[i][j + 1]) {
                    return false
                }
            }
        }
        return true
    }


    fun updateBoardView() {
        if (heightScore < score) heightScore = score
        for (i in 0 until size) {
            for (j in 0 until size) {
                textViewBlocks[i][j].text = if (board[i][j] == 0) "" else board[i][j].toString()
                viewBlocks(board[i][j], textViewBlocks[i][j])
            }
        }
        scoreView.text = score.toString()
        heightScoreView.text = heightScore.toString()
    }


    fun clearStEnXY() {
        stXY.clear()
        eXY.clear()
    }

    fun startXY() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (board[i][j] != 0) stXY.add(Pair(j, i))
            }
        }
    }


    fun endXY() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (board[i][j] != 0) eXY.add(Pair(j, i))
            }
        }
    }


    fun moveRight() {
        for (i in 0 until this.size) {
            for (j in this.size - 2 downTo 0) {
                var k = j
                while (k < this.size - 1 && board[i][k + 1] == 0) {
                    board[i][k + 1] = board[i][k]
                    board[i][k] = 0
                    k++
                }
                if (k < this.size - 1 && board[i][k] == board[i][k + 1]) {
                    score += board[i][k]
                    board[i][k + 1] *= 2
                    board[i][k] = 0
                }
            }
        }
    }


    fun moveLeft() {
        for (i in 0 until this.size) {
            for (j in 1 until this.size) {
                var k = j
                while (k > 0 && board[i][k - 1] == 0) {
                    board[i][k - 1] = board[i][k]
                    board[i][k] = 0
                    k--
                }
                if (k > 0 && board[i][k] == board[i][k - 1]) {
                    score += board[i][k]
                    board[i][k - 1] *= 2
                    board[i][k] = 0
                }
            }
        }
    }


    fun moveUp() {
        for (j in 0 until size) {
            for (i in 1 until size) {
                var k = i
                while (k > 0 && board[k - 1][j] == 0) {
                    board[k - 1][j] = board[k][j]
                    board[k][j] = 0
                    k--
                }
                if (k > 0 && board[k][j] == board[k - 1][j]) {
                    score += board[k][j]
                    board[k - 1][j] *= 2
                    board[k][j] = 0
                }
            }
        }
    }


    fun moveDown() {
        for (j in 0 until this.size) {
            for (i in this.size - 2 downTo 0) {
                var k = i
                while (k < size - 1 && board[k + 1][j] == 0) {
                    board[k + 1][j] = board[k][j]
                    board[k][j] = 0
                    k++
                }
                if (k < size - 1 && board[k][j] == board[k + 1][j]) {
                    score += board[k][j]
                    board[k + 1][j] *= 2
                    board[k][j] = 0
                }
            }
        }
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