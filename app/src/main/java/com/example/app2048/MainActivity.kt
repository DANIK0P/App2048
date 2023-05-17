package com.example.app2048

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs
import kotlin.random.Random


class MainActivity : AppCompatActivity() {


    private lateinit var scoreView: TextView
    private lateinit var heightScoreView: TextView
    private var score = 0
    private var heightScore = 0
    private lateinit var boardLayout: GridLayout
    private val textViewBlocks = mutableListOf(mutableListOf<TextView>())
    private var board = mutableListOf(mutableListOf<Int>())
    private var size = 4
    private lateinit var blockAnimation: AnimationBlocks
    private val startXY = mutableListOf<Pair<Int, Int>>()
    private val endXY = mutableListOf<Pair<Int, Int>>()



    @SuppressLint("ClickableViewAccessibility", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blockAnimation = AnimationBlocks()
        boardLayout = findViewById(R.id.boardLayout)
        scoreView = findViewById(R.id.scoreTextView)
        heightScoreView = findViewById(R.id.highScoreTextView)
        newGame()


        val gestureDetector =
            GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {

                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }

                override fun onFling(
                    e1: MotionEvent,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    val dx = e2.x - e1.x
                    val dy = e2.y - e1.y
                    val absDx = abs(dx)
                    val absDy = abs(dy)
                    val minGestureLength = 50
                    val minVelocity = 200

                    if (absDx > absDy && absDx > minGestureLength && abs(velocityX) > minVelocity) {
                        if (dx > 0) {
                            moveRight()
                        } else {
                            moveLeft()
                        }
                    } else if (absDy > absDx && absDy > minGestureLength && abs(velocityY) > minVelocity) {
                        if (dy > 0) {
                            moveDown()
                        } else {
                            moveUp()
                        }
                    } else {
                        return false
                    }

                    addRandomBlock()
                    if (heightScore < score) heightScore = score
                    if (startXY.isNotEmpty() && endXY.isEmpty()) {
                        animateBlock()
                        startXY.clear()
                        endXY.clear()
                    }
                    updateBoardView()


                    if (isGameOver()) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(R.string.game_over_title)
                            .setMessage(R.string.game_over_message)
                            .setPositiveButton(R.string.new_game) { _, _ ->
                                newGame()
                                updateBoardView()
                            }
                            .setNegativeButton(R.string.quit) { _, _ ->
                                finish()
                            }
                            .setCancelable(false)
                            .show()
                    }

                    return true
                }
            })
        boardLayout.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
    }


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


    @SuppressLint("ResourceAsColor")
    private fun newGame() {
        board = MutableList(size) { MutableList(size) { 0 } }
        textViewBlocks.clear()
        boardLayout.removeAllViews()
        score = 0
        for (i in 0 until size) {
            textViewBlocks.add(mutableListOf())
            for (j in 0 until size) {
                val textView = TextView(this)
                textView.text = ""
                textView.gravity = Gravity.CENTER
                textView.textSize = 24f
                textView.setBackgroundColor(R.color.middle_bg)
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


    private fun updateBoardView() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                textViewBlocks[i][j].text = if (board[i][j] == 0) "" else board[i][j].toString()
                viewBlocks(board[i][j], textViewBlocks[i][j])
            }
        }
        scoreView.text = score.toString()
        heightScoreView.text = heightScore.toString()
    }


    fun moveRight() {
        for (i in 0 until this.size) {
            for (j in this.size - 2 downTo 0) {
                var k = j
                var isNotNothing = false
                if (board[i][j] != 0) {
                    startXY.add(Pair(j, i))
                    isNotNothing = true
                }
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
                if (board[i][j] == 0 && isNotNothing) {
                    endXY.add(Pair(k + 1, i))
                } else {
                    endXY.add(Pair(j, i))
                }
            }
        }
    }


    fun moveLeft() {
        for (i in 0 until this.size) {
            for (j in 1 until this.size) {
                var k = j
                var isNotNothing = false
                if (board[i][j] != 0) {
                    startXY.add(Pair(j, i))
                    isNotNothing = true
                }
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
                if (board[i][j] == 0 && isNotNothing) {
                    endXY.add(Pair(k - 1, i))
                } else {
                    endXY.add(Pair(j, i))
                }
            }
        }
    }


    fun moveUp() {
        for (j in 0 until size) {
            for (i in 1 until size) {
                var k = i
                var isNotNothing = false
                if (board[i][j] != 0) {
                    startXY.add(Pair(j, i))
                    isNotNothing = true
                }
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
                if (board[i][j] == 0 && isNotNothing) {
                    endXY.add(Pair(j, k - 1))
                } else {
                    endXY.add(Pair(j, i))
                }
            }
        }
    }


    fun moveDown() {
        for (j in 0 until this.size) {
            for (i in this.size - 2 downTo 0) {
                var k = i
                var isNotNothing = false
                if (board[i][j] != 0) {
                    startXY.add(Pair(j, i))
                    isNotNothing = true
                }
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
                if (board[i][j] == 0 && isNotNothing) {
                    endXY.add(Pair(j, k + 1))
                } else {
                    endXY.add(Pair(j, i))
                }
            }
        }
    }

    private fun animateBlock() {
        for (i in 0 until startXY.size) {
            blockAnimation.animateBlock(
                textViewBlocks[startXY[i].first][startXY[i].second],
                startXY[i].first,
                startXY[i].second,
                endXY[i].first,
                endXY[i].second
            )
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


    fun btnClick(v: View) {
        newGame()
    }
}