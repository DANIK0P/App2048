package com.example.app2048

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs


class MainActivity : AppCompatActivity() {


    private lateinit var scoreView: TextView
    private lateinit var heightScoreView: TextView
    private lateinit var boardLayout: GridLayout
    private lateinit var board: Board


    @SuppressLint("ClickableViewAccessibility", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreView = findViewById(R.id.scoreTextView)
        heightScoreView = findViewById(R.id.highScoreTextView)
        boardLayout = findViewById(R.id.boardLayout)

        board = Board(boardLayout, scoreView, heightScoreView)
        board.newGame()


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

                    board.startXY()
                    if (absDx > absDy && absDx > minGestureLength && abs(velocityX) > minVelocity) {
                        if (dx > 0) {
                            board.moveRight()
                        } else {
                            board.moveLeft()
                        }
                    } else if (absDy > absDx && absDy > minGestureLength && abs(velocityY) > minVelocity) {
                        if (dy > 0) {
                            board.moveDown()
                        } else {
                            board.moveUp()
                        }
                    } else {
                        return false
                    }
                    board.endXY()

                    if (board.notAddBlock()) {
                        board.addRandomBlock()
                        board.updateBoardView()
                    }
                    board.clearStEnXY()


                    if (board.isGameOver()) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(R.string.game_over_title)
                            .setPositiveButton(R.string.new_game) { _, _ ->
                                board.newGame()
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


    fun btnClick(v: View) {
        board.newGame()
    }
}