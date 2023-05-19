package com.example.app2048

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private var size = 4
    private lateinit var board: MutableList<MutableList<Int>>
    private val stXY = mutableListOf<Pair<Int, Int>>()
    private val eXY = mutableListOf<Pair<Int, Int>>()

    @Test
    fun notAdd() {

        board = mutableListOf(
            mutableListOf(2, 0, 0, 0),
            mutableListOf(4, 2, 0, 0),
            mutableListOf(4, 2, 0, 0),
            mutableListOf(16, 4, 2, 0)
        )
        move(20)
        assertFalse(notAddBlock())
    }

    private fun move(j: Int) {
        var k = j
        while (k > 0) {
            startXY(board)
//        moveRight()
            moveLeft()
            endXY(board)
            notAddBlock()
            println(notAddBlock())
            println(stXY)
            println(eXY)
            stXY.clear()
            eXY.clear()
            k--
        }
    }

    private fun notAddBlock(): Boolean {
        if (stXY != eXY) return true
        return false
    }


    private fun startXY(board: MutableList<MutableList<Int>>) {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (board[i][j] != 0) stXY.add(Pair(j, i))
            }
        }
    }

    private fun endXY(board: MutableList<MutableList<Int>>) {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (board[i][j] != 0) eXY.add(Pair(j, i))
            }
        }
    }


    private fun moveRight() {
        for (i in 0 until this.size) {
            for (j in this.size - 2 downTo 0) {
                var k = j
                while (k < this.size - 1 && board[i][k + 1] == 0) {
                    board[i][k + 1] = board[i][k]
                    board[i][k] = 0
                    k++
                }
                if (k < this.size - 1 && board[i][k] == board[i][k + 1]) {
                    board[i][k + 1] *= 2
                    board[i][k] = 0
                }
            }
        }
    }

    private fun moveLeft() {
        for (i in 0 until this.size) {
            for (j in 1 until this.size) {
                var k = j
                while (k > 0 && board[i][k - 1] == 0) {
                    board[i][k - 1] = board[i][k]
                    board[i][k] = 0
                    k--
                }
                if (k > 0 && board[i][k] == board[i][k - 1]) {
                    board[i][k]
                    board[i][k - 1] *= 2
                    board[i][k] = 0
                }
            }
        }
    }
}