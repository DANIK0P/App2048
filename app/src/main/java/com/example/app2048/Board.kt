package com.example.app2048

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


// Класс, представляющий игровое поле
class Board(private val size: Int): AppCompatActivity() {
    private val board = mutableListOf(mutableListOf<Int>())
    private val boardSize = 4

    // Добавляет случайный блок со значением 2 или 4
    fun addRandomBlock() {
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until size) {
            for (j in 0 until size) {
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

    fun isGameOver(): Boolean {
        for (i in 0..3) {
            for (j in 0..3) {
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

    // Сдвигает блоки вправо
    fun moveRight(): Boolean {
        var moved=false
        for (i in 0 until size) {
            for (j in size - 2 downTo 0) {
                var k = j
                while (k < size - 1 && board[i][k + 1] == 0) {
                    board[i][k + 1] = board[i][k]
                    board[i][k] = 0
                    k++
                }
                if (k < size - 1 && board[i][k] == board[i][k + 1]) {
                    board[i][k + 1] *= 2
                    board[i][k] = 0
                    moved=true
                }
            }
        }
        return moved
    }

    // Сдвигает блоки влево
    fun moveLeft(): Boolean {
        var moved=false
        for (i in 0 until size) {
            for (j in 1 until size) {
                var k = j
                while (k > 0 && board[i][k - 1] == 0) {
                    board[i][k - 1] = board[i][k]
                    board[i][k] = 0
                    k--
                }
                if (k > 0 && board[i][k] == board[i][k - 1]) {
                    board[i][k - 1] *= 2
                    board[i][k] = 0
                    moved=true
                }
            }
        }
        return moved
    }

    // Сдвигает блоки вверх
    fun moveUp(): Boolean {
        var moved = false
        for (j in 0..3) {
            var k = 0
            for (i in 0..3) {
                if (k != -1 && board[i][j] == board[i][k]) {
                    board[i][k] *= 2
                    board[i][j] = 0
                    moved = true
                } else {
                    if (k + 1 != j) {
                        board[i][k + 1] = board[i][j]
                        board[i][j] = 0
                        moved = true
                    }
                    k++
                }
            }
        }
        return moved
    }

    fun moveDown(): Boolean {
        var moved=false
        for (j in 0 until size) {
            for (i in 1 until size) {
                var k = i
                while (k > 0 && board[k - 1][j] == 0) {
                    board[k - 1][j] = board[k][j]
                }
                if (k != -1 && board[i][j] == board[i][k]) {
                    board[i][k] *= 2
                    board[i][j] = 0
                    moved = true
                } else {
                    if (k + 1 != j) {
                        board[i][k + 1] = board[i][j]
                        board[i][j] = 0
                        moved = true
                    }
                    k++
                }
            }
        }
        return moved
    }

    private fun addNewTile() {
        val emptyTiles = mutableListOf<Pair<Int, Int>>()
        // Находим все пустые клетки
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (board[i][j] == 0) {
                    emptyTiles.add(Pair(i, j))
                }
            }
        }
        // Если есть пустые клетки, выбираем случайную и добавляем на нее новую плитку
        if (emptyTiles.isNotEmpty()) {
            val randomIndex = (0 until emptyTiles.size).random()
            val (row, col) = emptyTiles[randomIndex]
            board[row][col] = if (Random.nextDouble() < 0.9) 2 else 4
        }
    }

    private fun onSwipeLeft() {
        if (moveLeft()) {
            addNewTile()
            if (isGameOver()) {
                showGameOverDialog()
            }
        }
    }

    private fun onSwipeRight() {
        if (moveRight()) {
            addNewTile()
            if (isGameOver()) {
                showGameOverDialog()
            }
        }
    }

    private fun onSwipeUp() {
        if (moveUp()) {
            addNewTile()
            if (isGameOver()) {
                showGameOverDialog()
            }
        }
    }

    private fun onSwipeDown() {
        if (moveDown()) {
            addNewTile()
            if (isGameOver()) {
                showGameOverDialog()
            }
        }
    }

    private fun showGameOverDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.game_over_title)
            .setMessage(R.string.game_over_message)
            .setPositiveButton(R.string.new_game) { _, _ ->
                startLockTask()
            }
            .setNegativeButton(R.string.quit) { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }


}