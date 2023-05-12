package com.example.app2048

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GestureDetectorCompat


class MainActivity : AppCompatActivity() {

    private lateinit var boardLayout: GridLayout
    private lateinit var board: Board
    private lateinit var score: TextView
    private lateinit var hightScore: TextView


    @SuppressLint("ClickableViewAccessibility", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boardLayout = findViewById(R.id.boardLayout)

        // Создаем новую игру
        board = Board(4)

        // Добавляем два блока со значением 2
        board.addRandomBlock()
        board.addRandomBlock()

        // Обновляем отображение игрового поля
//        boardLayout.updateViewLayout(board)

        // Добавляем обработчик нажатия на клавиши
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
                    if (e1 == null || e2 == null) {
                        return false
                    }

                    // Вычисляем направление движения по скорости жеста
                    val dx = e2.x - e1.x
                    val dy = e2.y - e1.y
                    val absDx = Math.abs(dx)
                    val absDy = Math.abs(dy)
                    val minGestureLength = 50
                    val minVelocity = 200

                    if (absDx > absDy && absDx > minGestureLength && Math.abs(velocityX) > minVelocity) {
                        if (dx > 0) {
                            // Сдвигаем блоки вправо

                            board.moveRight()
                        } else {
                            // Сдвигаем блоки влево
                            board.moveLeft()
                        }
                    } else if (absDy > absDx && absDy > minGestureLength && Math.abs(velocityY) > minVelocity) {
                        if (dy > 0) {
                            // Сдвигаем блоки вниз
                            board.moveDown()
                        } else {
                            // Сдвигаем блоки вверх
                            board.moveUp()
                        }
                    } else {
                        return false
                    }

                    // Добавляем новый блок со значением 2 или 4
                    board.addRandomBlock()

                    // Обновляем отображение игрового поля
//                    boardView.updateBoard(board)

                    //// Проверяем, закончилась ли игра
                    if (board.isGameOver()) {
                        // Показываем диалоговое окно с сообщением о проигрыше
                        val builder = AlertDialog.Builder(this@MainActivity)
                        builder.setMessage(getString(R.string.game_over_message))
                            .setPositiveButton(getString(R.string.new_game)) { _, _ ->
                                // Начинаем новую игру
                                board = Board(4)
                                board.addRandomBlock()
                                board.addRandomBlock()
//                                boardLayout.updateBoard(board)
                            }
                            .setNegativeButton(getString(R.string.quit)) { _, _ ->
                                // Закрываем приложение
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
}