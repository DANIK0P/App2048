package com.example.app2048

import kotlin.random.Random

class Board {


    private var size = 4
    private var board = MutableList(size) { MutableList(size) { 0 } }
    private var score = 0
    private val stXY = mutableListOf<Pair<Int, Int>>()
    private val eXY = mutableListOf<Pair<Int, Int>>()

    fun getBoard(): MutableList<MutableList<Int>> {
        return board
    }

    fun getScore(): Int {
        return score
    }

    fun setBoard(board: MutableList<MutableList<Int>>) {
        this.board = board
    }

    fun setScore(score:Int) {
        this.score = score
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


    fun isGameOver(): Boolean {
        for (i in 0 until this.size) {
            for (j in 0 until this.size) {
                when {
                    board[i][j] == 0 -> return false
                    i < 3 && board[i][j] == board[i + 1][j] -> return false
                    j < 3 && board[i][j] == board[i][j + 1] -> return false
                }
            }
        }
        return true
    }


    fun notAddBlock(): Boolean {
        return stXY != eXY
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
}