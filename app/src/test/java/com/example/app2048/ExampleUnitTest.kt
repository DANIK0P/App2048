package com.example.app2048

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testAddRandomBlock() {
        val board = Board()
        board.setBoard(
            mutableListOf(
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0)
            )
        )
        board.addRandomBlock()
        assertTrue(board.getBoard().any { it.any { cell -> cell != 0 } })
    }

    @Test
    fun testIsGameOver() {
        val board = Board()
        board.setBoard(
            mutableListOf(
                mutableListOf(2, 4, 8, 16),
                mutableListOf(32, 64, 128, 256),
                mutableListOf(512, 1024, 2048, 4096),
                mutableListOf(16, 2, 4, 8)
            )
        )
        assertTrue(board.isGameOver())

        board.setBoard(
            mutableListOf(
                mutableListOf(2, 4, 8, 16),
                mutableListOf(32, 64, 128, 256),
                mutableListOf(512, 1024, 2048, 0),
                mutableListOf(0, 0, 0, 0)
            )
        )
        assertFalse(board.isGameOver())
    }

    @Test
    fun testMoveRight() {
        val board = Board()
        board.setBoard(
            mutableListOf(
                mutableListOf(0, 0, 2, 2),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0)
            )
        )
        board.moveRight()
        assertEquals(
            mutableListOf(
                mutableListOf(0, 0, 0, 4),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0)
            ), board.getBoard()
        )
    }

    @Test
    fun testMoveLeft() {
        val board = Board()
        board.setBoard(
            mutableListOf(
                mutableListOf(2, 2, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0)
            )
        )
        board.moveLeft()
        assertEquals(
            mutableListOf(
                mutableListOf(4, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0)
            ), board.getBoard()
        )
    }

    @Test
    fun testMoveUp() {
        val board = Board()
        board.setBoard(
            mutableListOf(
                mutableListOf(2, 0, 0, 0),
                mutableListOf(2, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0)
            )
        )
        board.moveUp()
        assertEquals(
            mutableListOf(
                mutableListOf(4, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0)
            ), board.getBoard()
        )
    }

    @Test
    fun testMoveDown() {
        val board = Board()
        board.setBoard(
            mutableListOf(
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(2, 0, 0, 0),
                mutableListOf(2, 0, 0, 0)
            )
        )
        board.moveDown()
        assertEquals(
            mutableListOf(
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(4, 0, 0, 0)
            ), board.getBoard()
        )
    }

    @Test
    fun testNotAddBlock() {
        val board = Board()
        board.setBoard(
            mutableListOf(
                mutableListOf(2, 4, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0)
            )
        )
        board.startXY()
        board.moveLeft()
        board.endXY()
        assertFalse(board.notAddBlock())
        board.clearStEnXY()

        board.setBoard(
            mutableListOf(
                mutableListOf(2, 2, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 0, 0, 0),
                mutableListOf(0, 4, 0, 4)
            )
        )
        board.startXY()
        board.moveRight()
        board.endXY()
        assertTrue(board.notAddBlock())
        board.clearStEnXY()
    }
}
