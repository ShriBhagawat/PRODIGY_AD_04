package com.example.tictactoegame

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val board = Array(3) { IntArray(3) }
    private var currentPlayer = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.lavender)

        initializeBoard()

        val buttons = Array(3) { arrayOfNulls<Button>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonId = "button_${i}${j}"
                val resourceId = resources.getIdentifier(buttonId, "id", packageName)
                buttons[i][j] = findViewById(resourceId)
                buttons[i][j]?.setOnClickListener { onButtonClick(i, j, buttons[i][j]!!) }
            }
        }

        val resetButton: Button = findViewById(R.id.button_reset)
        resetButton.setOnClickListener {
            initializeBoard()
            updateButtons()
        }
    }

    private fun initializeBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = 0
            }
        }
        currentPlayer = 1
    }

    private fun onButtonClick(row: Int, col: Int, button: Button) {
        if (board[row][col] == 0) {
            board[row][col] = currentPlayer
            button.text = if (currentPlayer == 1) "X" else "O"
            if (checkWin(row, col, currentPlayer)) {
                showWinnerDialog(currentPlayer)
                return
            }
            currentPlayer = if (currentPlayer == 1) 2 else 1
        }
    }

    private fun checkWin(row: Int, col: Int, player: Int): Boolean {
        // Check row
        if (board[row][0] == player && board[row][1] == player && board[row][2] == player)
            return true
        // Check column
        if (board[0][col] == player && board[1][col] == player && board[2][col] == player)
            return true
        // Check diagonals
        if (row == col && board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true
        if (row + col == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true
        return false
    }

    private fun showWinnerDialog(player: Int) {
        val winner = if (player == 1) "Player X" else "Player O"
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("$winner wins!")
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }


    private fun updateButtons() {
        val buttons = Array(3) { arrayOfNulls<Button>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonId = "button_${i}${j}"
                val resourceId = resources.getIdentifier(buttonId, "id", packageName)
                buttons[i][j] = findViewById(resourceId)
                buttons[i][j]?.text = ""
                buttons[i][j]?.isEnabled = true            }
        }
    }
}